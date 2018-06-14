package thiengo.com.br.fasteremail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.pchmn.materialchips.ChipView
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.ChipInterface
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import thiengo.com.br.fasteremail.data.Database
import thiengo.com.br.fasteremail.domain.User
import thiengo.com.br.fasteremail.util.Util

class MainActivity :
        AppCompatActivity(),
        ChipsInput.ChipsListener,
        TextWatcher {

    /*
     * Flag para que seja possível somente invocar o
     * algoritmo de extração de hashtag quando houver
     * uma.
     * */
    var hasTag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        et_message.addTextChangedListener(this)

        ci_contacts.addChipsListener( this )
        ci_contacts.setFilterableList( Database.getContacts(this) )

        /*
         * FilterableList vazio, mesmo vazio deve haver algum
         * vinculado ao ChipsInput.
         * */
        //ci_contacts.setFilterableList( arrayListOf() )

        /*
         * O código de Thread a seguir simula o carregamento remoto
         * de contatos a serem colocados no ChipsInput. Funciona sem
         * problemas, o FilterableList anterior é substituído pelo
         * atual, que está vazio de contatos.
         * */
        /*thread {
            SystemClock.sleep(3000)

            /*
             * A vinculação dos novos contatos deve ocorrer na Thread
             * principal, pois componentes visuais serão modificados.
             * */
            runOnUiThread {
                ci_contacts.setFilterableList( DataBase.getContacts(this) )
            }
        }*/
    }

    /*
     * Para a atualização dos título e ícone de topo
     * esquerdo da Toolbar.
     * */
    override fun onResume() {
        super.onResume()
        toolbar.setNavigationIcon(R.drawable.ic_account_plus_white_24dp)
        toolbar.setTitle("Email social para quem?")
    }

    /*
     * Para o carregamento do XML de menu e assim a
     * apresentação do ícone de "Enviar"
     * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        /*
         * Percorrendo todos os contatos em lista depois
         * do acionamento do item "Enviar".
         * */
        for( i in ci_contacts.selectedChipList ){
            Log.i("log", "User:");
            Log.i("log", " - Label: ${i.label}");
            Log.i("log", " - Info: ${i.info}");
        }
        Log.i("log", "Email:");
        Log.i("log", " - Mensagem: ${et_message.text}");

        return super.onOptionsItemSelected(item)
    }



    /*
     * Para adicionar um novo contato como um ChipView,
     * contato não presente na lista do usuário. Assim
     * que o email é reconhecido o contato é adicionado.
     * */
    override fun onTextChanged(contact: CharSequence?) {
        if( Util.isEmail( contact.toString() ) ){
            ci_contacts.addChip( getUserByEmail( contact.toString() ) )
        }
    }
    override fun onChipAdded(user: ChipInterface?, p1: Int) {}
    override fun onChipRemoved(user: ChipInterface?, p1: Int) {}

    /*
     * Retorna um usuário já presente na lista contida no
     * ChipsInput ou um novo User caso seja um novo endereço
     * de email.
     * */
    private fun getUserByEmail(contact: String): ChipInterface {
        /*
         * Para que a vírgula ou o espaço em branco não faça
         * parte do email.
         * */
        val email = contact.substring(0, contact.length - 1)

        for( user in ci_contacts.filterableList ){
            if( user.info.equals(email, true) ){
                return user
            }
        }

        return User(
                this,
                null,
                email.split("@")[0], /* Obtendo somente o nick antes do @ do endereço de email. */
                email
        )
    }



    /*
     * Para que seja possível obter o conteúdo informado pelo usuário
     * e se encontrada alguma hashtag ao final, troca-la por uma
     * imagem, ChipView.
     * */
    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if( Util.containsHashTag( text.toString() ) ){
            /*
             * Enquanto o padrão de hashtag permanecer sendo
             * encontrado no text informado pelo usuário mantanha
             * entrando aqui para poder gravar na flag que há uma
             * hashtag no text.
             * */
            hasTag = true
        }
        else if( hasTag ){
            /*
             * A entrada aqui somente ocorre porque não mais está
             * sendo encontrado um padrão de hashtag ao final do
             * text informado pelo usuário, porém a flag hasTag
             * aponta que ainda há uma hashtag não extraída para
             * dar lugar a uma imagem.
             * */
            hasTag = false
            changeHashTagToImage( text.toString() )
        }

    }
    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    private fun changeHashTagToImage(text: String){
        /* Obtendo o último hashtag presente em texto. */
        val match = Util.getHashTagMatch(text)

        while( match.find() ) {
            val hashTag = match.group()
            val beginPosition = text.indexOf(hashTag)
            val startPosition = text.indexOf(hashTag) + hashTag.length

            /* Criando Bitmap de um ChipView. */
            val chipView = ChipView(this)
            chipView.label = hashTag.replace("#", "") /* O # não ficará no Bitmap gerado de ChipView. */
            val imgBitmap = Util.createBitmapFromView( chipView )

            /*
             * A obtenção da Spanned String do EditText como SpannableStringBuilder
             * é necessária para que seja possível remover a hashtag em text e
             * deixar somente a versão em imagem.
             * */
            var spannable = et_message.text as SpannableStringBuilder
            spannable = Util.retrieveSpannableWithBitmap(
                this,
                spannable,
                imgBitmap,
                beginPosition,
                startPosition )

            et_message.setText( spannable )
            et_message.setSelection( spannable.length ) /* Para manter o cursor no final do text no EditText. */
        }
    }
}
