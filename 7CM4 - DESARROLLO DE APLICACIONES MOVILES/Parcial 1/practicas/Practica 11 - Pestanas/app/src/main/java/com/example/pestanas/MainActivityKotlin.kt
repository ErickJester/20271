package com.example.pestanas

import android.os.Bundle
import android.widget.TabHost
import androidx.appcompat.app.AppCompatActivity

// EJEMPLO 3 (Kotlin): TabHost con tres pestañas, cada una con su contenido.
class MainActivityKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs3)
        val tabHost = findViewById<TabHost>(R.id.tabHost)
        tabHost.setup()

        val tabSpec1 = tabHost.newTabSpec("Tab 1")         // Primera pestaña
        tabSpec1.setContent(R.id.tab1)
        tabSpec1.setIndicator("Pestaña 1")
        tabHost.addTab(tabSpec1)

        val tabSpec2 = tabHost.newTabSpec("Tab 2")         // Segunda pestaña
        tabSpec2.setContent(R.id.tab2)
        tabSpec2.setIndicator("Pestaña 2")
        tabHost.addTab(tabSpec2)

        val tabSpec3 = tabHost.newTabSpec("Tab 3")         // Tercera pestaña
        tabSpec3.setContent(R.id.tab3)
        tabSpec3.setIndicator("Pestaña 3")
        tabHost.addTab(tabSpec3)
    }
}
