package com.example.planandpricing


import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding

open class BaseActivity: AppCompatActivity()  {
    private var bottomInsetView: View? = null
    private var topInsetView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // WindowCompat.setDecorFitsSystemWindows(window, false)

        val decor = window.decorView as ViewGroup
        bottomInsetView = View(this).apply { setBackgroundColor(Color.WHITE) }
        topInsetView = View(this).apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorPrimary
                )
            )
        }
        decor.addView(
            topInsetView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                0,
                Gravity.TOP
            )
        )

        decor.addView(
            bottomInsetView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                0,
                Gravity.BOTTOM
            )
        )

        ViewCompat.setOnApplyWindowInsetsListener(decor) { _, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            bottomInsetView?.updateLayoutParams<FrameLayout.LayoutParams> {
                height = maxOf(sysBars.bottom, ime.bottom)
                gravity = Gravity.BOTTOM
            }
            topInsetView?.updateLayoutParams<FrameLayout.LayoutParams> {
                height = sysBars.top
                gravity = Gravity.TOP
            }
            insets
        }
        ViewCompat.requestApplyInsets(decor)
        enableEdgeToEdge()

        val root = findViewById<View>(android.R.id.content)
        root?.let {

            ViewCompat.setOnApplyWindowInsetsListener(it) { view, insets ->
                val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val ime = insets.getInsets(WindowInsetsCompat.Type.ime())

                val bottom = maxOf(sysBars.bottom, ime.bottom)
                view.updatePadding(
                    top = sysBars.top,
                    bottom = bottom
                )
                WindowInsetsCompat.CONSUMED
            }
            ViewCompat.requestApplyInsets(it)
        }

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = true
        }
    }

}

