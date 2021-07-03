package ru.serzh272.testapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.serzh272.testapplication.R
import ru.serzh272.testapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}