package com.example.a5ariketa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.a5ariketa.ui.theme._5AriketaTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5AriketaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GGIKalkulua(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GGIKalkulua(modifier: Modifier = Modifier) {
    var pisua by remember { mutableStateOf("") }
    var altuera by remember { mutableStateOf("") }
    var adina by remember { mutableStateOf("") }
    var sexua by remember { mutableStateOf("") }

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val image = painterResource(R.drawable.ariketa5fondoa)
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Text(
                text = "Gorputz Gantzaren Indizearen Kalkulagailua",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer (modifier = modifier.height(36.dp))

            TextField(
                value = pisua,
                onValueChange = { pisua = it },
                label = {
                    Text(
                        "Pisua (kg)"
                    )
                }
            )


            TextField(
                value = altuera,
                onValueChange = { altuera = it },
                label = {
                    Text(
                        "Altuera (m)"
                    )
                }
            )

            TextField(
                value = adina,
                onValueChange = { adina = it },
                label = {
                    Text(
                        "Adina"
                    )
                }
            )

            TextField(
                value = sexua,
                onValueChange = { sexua = it },
                label = {
                    Text(
                        "Sexua(g/e)"
                    )
                }
            )

            var gmiErantzuna = "GMI: "
            var ggiErantzuna = "GGI: "


            if (pisua.isNotEmpty() && altuera.isNotEmpty() && adina.isNotEmpty() && sexua.isNotEmpty()) {

                val pisuaZbk = pisua.replace(",",".").toDoubleOrNull()
                val altueraMetrotan = altuera.replace(",",".").toDoubleOrNull()?.div(100)
                val adinaZbk = adina.toDoubleOrNull()

                val sexuaGarbia = sexua.trim().lowercase()

                val sexuaZbk: Double? = when (sexuaGarbia) {
                    "gizon", "g" -> 1.0
                    "emakumea", "e" -> 0.0
                    else -> null
                }

                if (pisuaZbk != null && altueraMetrotan != null && altueraMetrotan > 0 && adinaZbk != null && sexuaZbk != null) {
                    val gmi = pisuaZbk / (altueraMetrotan * altueraMetrotan)
                    val ggi = (1.20 * gmi) + (0.23 * adinaZbk) - (10.8 * sexuaZbk) - 5.4

                    val gmiBiribilduta = Math.round(gmi * 100.0) / 100.0
                    val ggiBiribilduta = Math.round(ggi * 100.0) / 100.0

                    gmiErantzuna = "GMI: $gmiBiribilduta"
                    ggiErantzuna = "GGI%: $ggiBiribilduta%"
                }

            } else {
                gmiErantzuna = "Bete datu guztiak"
                ggiErantzuna = ""
            }

            Text(
                text = gmiErantzuna
            )
1
            Text(
                text = ggiErantzuna
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GrettingPreview() {
    _5AriketaTheme() {
        GGIKalkulua()
    }
}
