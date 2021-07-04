package ru.serzh272.testapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.serzh272.testapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}