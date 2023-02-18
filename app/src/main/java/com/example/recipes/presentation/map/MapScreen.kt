package com.example.recipes.presentation.map

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIconDefaults.Text
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun ViewMap(){
   /* val marker = LatLng(28.044195,-16.5363842)
    val properties by remember { mutableStateOf( MapProperties(mapType = MapType.HYBRID)) }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
    ){
        Marker(position = marker, title = "El Recipe", snippet = "This is your Selection Recipe")
    }*/

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center) {
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
            Button(onClick = {
                // Lanza la actividad de Google Maps
                val uri = Uri.parse("geo:0,0?q=1600 Amphitheatre Parkway, Mountain View, CA")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                launcher.launch(intent)
            }) {
                Text(text ="Ver Mapa" )
            }
        }

    }
}