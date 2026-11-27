package com.fixit.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fixit.mobile.ui.FixItApp
import com.fixit.mobile.ui.theme.FixItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FixItTheme { FixItApp() } }
    }
}
