package com.example.prak17_1
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.PreferenceManager

import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit  var login: EditText
    lateinit  var pass: EditText
    lateinit var pref: SharedPreferences
    private var prefs: SharedPreferences? = null
    private var listener: OnSharedPreferenceChangeListener? = null
    private var cb1 = false
    private var cb2 = false

    private var list1: String? = null
    private var et1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);

        listener = OnSharedPreferenceChangeListener { prefs, key ->
            Toast.makeText(
                this@MainActivity,
                key,
                Toast.LENGTH_SHORT
            ).show()
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

    }


    fun handler(v: View) {
        if (v.getId() === R.id.save) {
            var pref = getPreferences(MODE_PRIVATE)
            val ed: SharedPreferences.Editor = pref.edit()
            ed.putString("login", login.getText().toString())
            ed.putString("password", pass.getText().toString())

            ed.apply()
        }
        if (v.getId() === R.id.load) {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        pref = getPreferences(MODE_PRIVATE)
                        val loginString = pref.getString("login", "")
                        login.setText(loginString)
                        pass.setText(pref.getString("password", ""))
                        Toast.makeText(this@MainActivity, "Данные загружены", Toast.LENGTH_SHORT).show()
                        val loginTextView = findViewById<TextView>(R.id.alert)
                        loginTextView.text = "Ваш логин: $loginString"
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Вы уверены, что хотите загрузить данные?").setPositiveButton("Да", dialogClickListener)
                .setNegativeButton("Нет", dialogClickListener).show()
        }
    }



    fun setPref(v: View?) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun getPref(v: View?) {
        cb1 = prefs!!.getBoolean("cb1", true)
        cb2 = prefs!!.getBoolean("cb2", true)
        list1 = prefs!!.getString("list1", "something")
        et1 = prefs!!.getString("et1", "nothing")
        Toast.makeText(this, "$cb1, $cb2, $list1, $et1", Toast.LENGTH_LONG).show()
    }
}