package com.example.kursovavavaaa.ui.fragments
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

class BMIScaleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private var bmiValue: Float = 0f
    private var pointerPosition: Float = 0f

    // Colors for the BMI categories
    private val underweightColor = Color.parseColor("#B3E5FC")  // Light blue
    private val normalColor = Color.parseColor("#81C784")      // Light green
    private val overweightColor = Color.parseColor("#FFF176")   // Yellow
    private val obesityColor = Color.parseColor("#FF8A80")      // Red

    init {
        paint.isAntiAlias = true
        paint.textSize = 30f
    }

    // Set the BMI value for the scale
    fun setBmiValue(bmi: Float) {
        bmiValue = bmi
        // Redraw the view after setting the BMI value
        invalidate()
    }

    // This method is called when the layout is done and the size is available
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        // Calculate the pointer position based on BMI
        calculatePointerPosition()
    }

    private fun calculatePointerPosition() {
        // We assume BMI values range from 10 to 40, which corresponds to 0% to 100% of the scale width
        val minBmi = 10f
        val maxBmi = 40f

        // Clamp the BMI value to be within the scale range
        val clampedBmi = max(minBmi, min(bmiValue, maxBmi))

        // Calculate pointer position as a percentage of the scale width
        val scaleWidth = width * 0.8f // Scale takes 80% of the width
        val scaleStart = width * 0.1f // Start of the scale is at 10% of the width
        val position = scaleStart + ((clampedBmi - minBmi) / (maxBmi - minBmi)) * scaleWidth

        pointerPosition = position
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val scaleHeight = height * 1f   // Scale height is 50% of view height
        val scaleWidth = width * 0.8f   // Scale width is 80% of the view width
        val scaleStart = width * 0.1f   // Start of the scale is at 10% of the view width

        // Draw the scale sections
        paint.shader = null
        paint.style = Paint.Style.FILL

        // Underweight section
        paint.color = underweightColor
        canvas.drawRect(scaleStart, 0f, scaleStart + scaleWidth * 0.25f, scaleHeight, paint)

        // Normal section
        paint.color = normalColor
        canvas.drawRect(scaleStart + scaleWidth * 0.25f, 0f, scaleStart + scaleWidth * 0.5f, scaleHeight, paint)

        // Overweight section
        paint.color = overweightColor
        canvas.drawRect(scaleStart + scaleWidth * 0.5f, 0f, scaleStart + scaleWidth * 0.75f, scaleHeight, paint)

        // Obesity section
        paint.color = obesityColor
        canvas.drawRect(scaleStart + scaleWidth * 0.75f, 0f, scaleStart + scaleWidth, scaleHeight, paint)

        // Draw the pointer
        paint.color = Color.BLACK
        paint.strokeWidth = 6f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(pointerPosition, 0f, pointerPosition, scaleHeight, paint)

        // Draw the scale text (Optional)
        paint.color = Color.BLACK
        paint.textSize = 30f
        canvas.drawText("10", scaleStart, scaleHeight + 40f, paint)
        canvas.drawText("40", scaleStart + scaleWidth, scaleHeight + 40f, paint)
    }
}
