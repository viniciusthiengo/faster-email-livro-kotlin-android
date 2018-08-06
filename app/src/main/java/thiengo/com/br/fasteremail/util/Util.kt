package thiengo.com.br.fasteremail.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.View
import android.widget.LinearLayout
import thiengo.com.br.fasteremail.R
import java.util.regex.Pattern

fun isEmail(email: String) =
    Pattern
        .compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4}[ ,]{1})$")
        .matcher(email)
        .matches()

fun getImageResource(text: String?) : Int {
    val t = text ?: ""
    val letra =
        if( t[0].equals('a', true) ) R.drawable.a
        else if( t[0].equals('b', true) ) R.drawable.b
        else if( t[0].equals('c', true) ) R.drawable.c
        else if( t[0].equals('d', true) ) R.drawable.d
        else if( t[0].equals('e', true) ) R.drawable.e
        else if( t[0].equals('f', true) ) R.drawable.f
        else if( t[0].equals('g', true) ) R.drawable.g
        else if( t[0].equals('h', true) ) R.drawable.h
        else if( t[0].equals('i', true) ) R.drawable.i
        else if( t[0].equals('j', true) ) R.drawable.j
        else if( t[0].equals('k', true) ) R.drawable.k
        else if( t[0].equals('l', true) ) R.drawable.l
        else if( t[0].equals('m', true) ) R.drawable.m
        else if( t[0].equals('n', true) ) R.drawable.n
        else if( t[0].equals('o', true) ) R.drawable.o
        else if( t[0].equals('p', true) ) R.drawable.p
        else if( t[0].equals('q', true) ) R.drawable.q
        else if( t[0].equals('r', true) ) R.drawable.r
        else if( t[0].equals('s', true) ) R.drawable.s
        else if( t[0].equals('t', true) ) R.drawable.t
        else if( t[0].equals('u', true) ) R.drawable.u
        else if( t[0].equals('v', true) ) R.drawable.v
        else if( t[0].equals('w', true) ) R.drawable.w
        else if( t[0].equals('x', true) ) R.drawable.x
        else if( t[0].equals('y', true) ) R.drawable.y
        else if( t[0].equals('z', true) ) R.drawable.z
        else R.drawable.background

    return letra
}

fun containsHashTag(texto: String) =
    texto.contains("\\B(\\#[A-zÀ-úA-zÀ-ÿ]+\\b)(?![^A-zÀ-úA-zÀ-ÿ])".toRegex())

/*
 * Definindo o padrão de expressão rgular e retornando o
 * match dele direto do texto que o usuário informou.
 * */
fun getHashTagMatch(text: String) =
    Pattern
        .compile("\\B(\\#[A-zÀ-úA-zÀ-ÿ]+\\b)")
        .matcher(text)

fun createBitmapFromView(view: View): Bitmap {
    val width: Int
    val height: Int

    /*
     * Em algumas versões do Android o view.measuredHeight não
     * será maior do que 0, por isso a necessidade desses
     * condicionais e algoritmos específicos.
     * */
    if(view.measuredHeight <= 0) {
        view.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        width = view.measuredWidth
        height = view.measuredHeight
        view.layout(0, 0, width, height)
    }
    else {
        width = view.layoutParams.width
        height = view.layoutParams.height
        view.layout(view.left, view.top, view.right, view.bottom)
    }
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    view.draw( Canvas(bitmap) )

    return bitmap
}

fun retrieveSpannableWithBitmap(
    context: Context,
    spannable: SpannableStringBuilder,
    bitmap: Bitmap,
    startPositionTexto: Int,
    endPositionTexto: Int ): SpannableStringBuilder {

    /* Deletando a parte de texto que contém a hashtag. */
    spannable.delete( startPositionTexto, endPositionTexto )

    /*
     * Criando um novo Drawable partindo do Bitmap informado
     * como parâmetro.
     * */
    val imgDrawable = BitmapDrawable( context.resources, bitmap )
    imgDrawable.setBounds(
        0,
        0,
        imgDrawable.getIntrinsicWidth(),
        imgDrawable.getIntrinsicHeight() )

    /*
     * Colocando a nova ImageSpan oculpando um único caractere
     * no conteúdo total da Spanned String.
     * */
    val imgSpan = ImageSpan(imgDrawable, ImageSpan.ALIGN_BASELINE)
    spannable.setSpan(
        imgSpan, // Objeto de estilo, Imagem, vinculado a String.
        startPositionTexto, // Posição inicial para a aplicação do estilo.
        startPositionTexto + 1, // Os caracteres para receber o estilo.
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE ) // Estilo até o último char definido. Ou seja, o char startPositionTexto + 1. Depois desse o estilo não mais funciona.

    return spannable
}