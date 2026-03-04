package com.example.toeic_app.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

object PdfHelper {
    fun generatePdf(
        context: Context,
        uri: Uri,
        title: String,
        questionId: Int,
        prompt: String,
        answer: String
    ) {
        val document = PdfDocument()

        // Page info: A4 size (595 x 842 pt)
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)

        val canvas = page.canvas
        
        val titlePaint = TextPaint().apply {
            color = Color.BLACK
            textSize = 24f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        
        val headerPaint = TextPaint().apply {
            color = Color.BLACK
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        val textPaint = TextPaint().apply {
            color = Color.DKGRAY
            textSize = 14f
        }

        val margin = 50f
        val startY = 50f
        var currentY = startY
        val contentWidth = pageInfo.pageWidth - (margin * 2).toInt()

        // Draw Title (e.g. Test 1 - Question 6)
        canvas.drawText("$title - Question $questionId", margin, currentY, titlePaint)
        currentY += 40f
        
        // Draw Prompt Header
        canvas.drawText("Prompt:", margin, currentY, headerPaint)
        currentY += 25f
        
        // Draw Prompt Text using StaticLayout for automatic line wrapping
        val promptLayout = StaticLayout.Builder.obtain(prompt, 0, prompt.length, textPaint, contentWidth)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(1.0f, 1.0f)
            .setIncludePad(false)
            .build()
            
        canvas.save()
        canvas.translate(margin, currentY)
        promptLayout.draw(canvas)
        canvas.restore()
        
        currentY += promptLayout.height + 40f

        // Draw Answer Header
        canvas.drawText("Your Answer:", margin, currentY, headerPaint)
        currentY += 25f

        // Draw Answer Text
        val answerLayout = StaticLayout.Builder.obtain(answer, 0, answer.length, textPaint, contentWidth)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(1.0f, 1.0f)
            .setIncludePad(false)
            .build()
            
        canvas.save()
        canvas.translate(margin, currentY)
        answerLayout.draw(canvas)
        canvas.restore()

        document.finishPage(page)

        try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                document.writeTo(outputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            document.close()
        }
    }
}
