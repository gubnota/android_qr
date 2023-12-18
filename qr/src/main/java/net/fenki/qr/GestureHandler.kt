package net.fenki.qr

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class GestureHandler(private val listener: OnSwipeListener) : View.OnTouchListener {
    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(listener.getContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                val deltaX = e2.x - e1.x
                val deltaY = e2.y - e1.y
                if(Math.abs(deltaX) < 10) return false;
                if (Math.abs(deltaX) > Math.abs(deltaY) && deltaX < 0) {
                    listener.onSwipeLeft()
                    return true
                }
                listener.onSwipeRight()
                return false
            }
        })
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    interface OnSwipeListener {
        fun onSwipeLeft()
        fun onSwipeRight()
        fun getContext(): Context
    }
}