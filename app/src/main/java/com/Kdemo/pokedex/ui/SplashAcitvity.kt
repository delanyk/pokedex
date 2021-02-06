package com.Kdemo.pokedex.ui

import android.content.Intent
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.Kdemo.pokedex.MainActivity
import com.Kdemo.pokedex.R

class SplashAcitvity : AppCompatActivity() {

    // constant time delay
    private val SPLASH_DELAY: Long = 2000;

    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitvity)

        window.setBackgroundDrawable(null)

        // methods
        initializeView()
        animateLogo()
        goToMainActivity()
    }

    private fun initializeView() {
        imageView = findViewById(R.id.splashView)
    }
    private fun animateLogo() {
        var fadiningAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        fadiningAnimation.duration = SPLASH_DELAY

        this.imageView!!.startAnimation(fadiningAnimation)

    }
    private fun goToMainActivity(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_DELAY)
    }
}
