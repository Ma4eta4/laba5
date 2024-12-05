package com.example.labaratornaya5mobliki

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var editTextData: EditText
    private lateinit var textViewData: TextView

    private val sharedPreferencesName = "AppPreferences"
    private val fileName = "dataFile.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextData = findViewById(R.id.editTextData)
        textViewData = findViewById(R.id.textViewData)
        val buttonSavePreferences = findViewById<Button>(R.id.buttonSavePreferences)
        val buttonSaveFile = findViewById<Button>(R.id.buttonSaveFile)
        val buttonLoadPreferences = findViewById<Button>(R.id.buttonLoadPreferences) // Кнопка загрузки из SharedPreferences
        val buttonLoadFile = findViewById<Button>(R.id.buttonLoadFile) // Кнопка загрузки из файла

        // Сохранение в SharedPreferences
        buttonSavePreferences.setOnClickListener {
            saveToPreferences(editTextData.text.toString())
        }

        // Сохранение во внешний файл
        buttonSaveFile.setOnClickListener {
            saveToFile(editTextData.text.toString())
        }

        // Загрузка данных из SharedPreferences по кнопке
        buttonLoadPreferences.setOnClickListener {
            loadFromPreferences()
        }

        // Загрузка данных из файла по кнопке
        buttonLoadFile.setOnClickListener {
            loadFromFile()
        }
    }

    private fun saveToPreferences(data: String) {
        val sharedPreferences = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("savedData", data)
            apply()
        }
        textViewData.text = "Данные сохранены в SharedPreferences: $data"
    }

    private fun loadFromPreferences() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val savedData = sharedPreferences.getString("savedData", "Нет данных")
        textViewData.text = "Данные из SharedPreferences: $savedData"
    }

    private fun saveToFile(data: String) {
        val file = File(getExternalFilesDir(null), fileName)
        try {
            FileOutputStream(file).use { stream ->
                stream.write(data.toByteArray())
            }
            textViewData.text = "Данные сохранены во внешний файл: $data"
        } catch (e: IOException) {
            e.printStackTrace()
            textViewData.text = "Ошибка при записи данных в файл."
        }
    }

    private fun loadFromFile() {
        val file = File(getExternalFilesDir(null), fileName)
        if (file.exists()) {
            val data = file.readText()
            textViewData.text = "Данные из файла: $data"
        } else {
            textViewData.text = "Файл не найден."
        }
    }
}