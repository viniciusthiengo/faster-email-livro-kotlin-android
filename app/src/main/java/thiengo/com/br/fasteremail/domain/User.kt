package thiengo.com.br.fasteremail.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.pchmn.materialchips.model.ChipInterface
import com.squareup.picasso.Picasso
import thiengo.com.br.fasteremail.util.ImageReceiver
import thiengo.com.br.fasteremail.util.Util


class User(
        private val context: Context, /* Para que seja possível o uso da API de carregamento d imagens remotas */
        private var avatarUri: Uri?, /* Agora var para que seja possível colocar o valor como null quando avatarDrawable estiver preenchido. */
        private val label: String,
        private val info: String ) : ChipInterface {

    /*
     * Necessário ter o Target (ImageReceiver) como propriedade
     * de classe. Caso contrário o carregamento das imagens
     * na primeira abertura do aplicativo não ocorre.
     * */
    private val receiver: ImageReceiver
    private var avatarDrawable: Drawable? = null

    init {
        receiver = ImageReceiver(this)
        downloadImage()
    }

    override fun getId() = null

    override fun getAvatarUri() = avatarUri

    override fun getAvatarDrawable() = avatarDrawable

    override fun getLabel() = label

    override fun getInfo() = info

    /*
     * Método necessário para que ao final do carregamento
     * da imagem remota, ela possa ser colocada no local
     * da imagem local (letra de nome) inicialmente colocada
     * em avatarDrawable.
     * */
    fun setAvatarDrawable( bitmap: Bitmap? ){
        avatarDrawable = BitmapDrawable(context.resources, bitmap)
    }

    private fun downloadImage(){
        /*
         * Hack code para que enquanto o status da imagem
         * remota seja "carregando", uma imagem de perfil
         * com a primeira letra do nome dele seja utilizada.
         * */
        avatarDrawable = ContextCompat.getDrawable( context, Util.getImageResource(label) )

        /*
         * Carregando a imagem remota com a API Picasso.
         * */
        if( avatarUri != null ) {
            Picasso.get()
                    .load(avatarUri.toString())
                    .into(receiver)
        }

        /*
         * Colocando avatarUri com o valor null nós estamos
         * garantindo que somente avatarDrawable será utilizado
         * como recurso de imagem nos ChipsView e ChipsContact
         * da API de Chips em uso. Ele é colocado como null aqui,
         * pois primeiro temos de passar o path da imagem para a
         * Picasso API realizar o download correto.
         * */
        avatarUri = null
    }
}