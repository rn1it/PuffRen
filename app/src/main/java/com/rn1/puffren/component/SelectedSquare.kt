package com.rn1.puffren.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R

class SelectedSquare: ShapeDrawable(object : Shape(){

    override fun draw(canvas: Canvas, paint: Paint) {

        paint.color = PuffRenApplication.instance.getColor(R.color.orange_ffa626)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = PuffRenApplication.instance.resources
            .getDimensionPixelSize(R.dimen.edge_selected_package).toFloat()
        canvas.drawRect(0f, 0f, this.width, this.height, paint)
    }

})