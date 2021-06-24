package com.backbase.assignment.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.backbase.assignment.R

class RatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var _maxProgress: Int = 100

    private var _progress: Int = 0

    var maxProgress: Int
        get() = _maxProgress
        set(value) {
            _maxProgress = value
            invalidate()
        }

    var progress: Int
        get() = _progress
        set(value) {
            _progress = value
            invalidate()
        }

    private val progressYellowColor = ContextCompat.getColor(context, R.color.yellowProgress)
    private val progressGreenColor = ContextCompat.getColor(context, R.color.greenProgress)
    private val circleYellowColor = ContextCompat.getColor(context, R.color.yellowProgressBg)
    private val circleGreenColor = ContextCompat.getColor(context, R.color.greenProgressBg)


    private val rect = RectF()
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var isAnimating = false
    private val animator = ValueAnimator.ofInt()

    init {

        if (attrs != null) {
            val array = context.obtainStyledAttributes(
                attrs, R.styleable.RankingViewStyle, defStyleAttr, 0
            )

            _maxProgress = array.getInteger(
                R.styleable.RankingViewStyle_android_max, _maxProgress
            )

            _progress = array.getInteger(
                R.styleable.RankingViewStyle_android_progress, _progress
            )

            array.recycle()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        val size = Math.min(width, height)
        progressPaint.style = Paint.Style.FILL
        progressPaint.strokeWidth = 8f
        progressPaint.color = ContextCompat.getColor(context, R.color.backgroundRating)

        rect.apply {
            left = width / 2f - size / 2f
            right = width / 2f + size / 2f
            top = 2f
            bottom = size.toFloat() - 3f
        }
        canvas.drawArc(rect, 360f, 360f, false, progressPaint)

        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = 6f
        progressPaint.color = if (progress <= 50) circleYellowColor else circleGreenColor

        canvas.drawArc(rect, 360f, 360f, false, progressPaint)

        progressPaint.color = if (progress <= 50) progressYellowColor else progressGreenColor

        canvas.drawArc(rect, -90f, 360f * progress / maxProgress, false, progressPaint)
        progressPaint.color = Color.WHITE
        progressPaint.style = Paint.Style.FILL_AND_STROKE
        progressPaint.strokeWidth = 0f
        progressPaint.textSize = 40f
        progressPaint.textAlign = Paint.Align.CENTER

        canvas.drawText(
            progress.toString(),
            width / 2f,
            (rect.height() / 2)+20f,
            progressPaint
        )
        progressPaint.textSize = 20f
        progressPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("%", (30f + width / 2f), (rect.height() / 2)-5f, progressPaint)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        if (superState != null && isSaveEnabled) {
            return SavedState(superState).apply {
                maxProgress = _maxProgress
                progress = _progress
            }
        }
        return superState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        _maxProgress = state.maxProgress ?: _maxProgress
        _progress = state.progress ?: _progress
    }

    class SavedState : BaseSavedState {

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }

        var progress: Int? = null
        var maxProgress: Int? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(inState: Parcel) : super(inState) {
            progress = inState.readInt()
            maxProgress = inState.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            progress?.let { out.writeInt(it) }
            maxProgress?.let { out.writeInt(it) }
        }
    }
}