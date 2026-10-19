package com.example.bekabadgas.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun MainScreen(
    onItemClick: (NavKey) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var service by remember { mutableStateOf("1. Газ жиҳозлари носозлиги бўйича") }
    var address by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    val services = listOf(
        "1. Газ жиҳозлари носозлиги бўйича",
        "2. Газ сизиб чиқиши (Шошилинч)",
        "3. Дудбуронларни тозалаш бўйича",
        "4. Газ лойиҳа ва смета ҳужжатлари"
    )

    fun triggerVibration() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(80)
        }
    }

    fun dialPhone(number: String) {
        triggerVibration()
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${number.replace(" ", "")}"))
        context.startActivity(intent)
    }

    fun openUrl(url: String) {
        triggerVibration()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🏢 BRAND HEADER
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "«BEKABAD UNITED GAS GROUP»",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Масъулияти Чекланган Жамияти",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🚨 ФАВҚУЛОДДА ТЕЗКОР ЧАҚИРУВ (EMERGENCY SPEED DIAL)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF991B1B), Color(0xFFDC2626))
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Emergency",
                        tint = Color(0xFFFACC15)
                    )
                    Text(
                        text = "ФАВҚУЛОДДА ТЕЗКОР ЧАҚИРУВ",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 104 Gaz Avariya Button
                Button(
                    onClick = { dialPhone("104") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F1D1D)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Газ авария хизмати", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                            Text("🔥 104 АВАРИЯ", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                        Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Dispetcher Button: +998 99 823 01 88
                Button(
                    onClick = { dialPhone("+998998230188") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Корхона бош диспетчери", fontSize = 11.sp, color = Color(0xFF64748B))
                            Text("📞 +998 99 823 01 88", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
                        }
                        Icon(Icons.Default.Call, contentDescription = "Call", tint = Color(0xFF0D47A1))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bekobod GTB ADX: +998 55 903 22 14
                Button(
                    onClick = { dialPhone("+998559032214") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Бекобод шаҳар газ ГТБ АДХ", fontSize = 11.sp, color = Color(0xFF64748B))
                            Text("☎️ +998 55 903 22 14", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                        }
                        Icon(Icons.Default.Call, contentDescription = "Call", tint = Color(0xFFDC2626))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Qisqa favqulodda raqamlar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { dialPhone("101") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text("101 Ёнғин", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { dialPhone("103") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text("103 Тез ёрдам", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { dialPhone("112") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text("112 ФВВ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔄 TELEGRAM BOT & WEBSITE KOLLABORATSIYASI
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { openUrl("https://t.me/bekabad_united_gas_group_bot") },
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088CC)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("💬 Telegram Бот", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Button(
                onClick = { openUrl("https://bekabadgas.surge.sh") },
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("🌐 Расмий сайт", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📝 ОНЛАЙН АРИЗА ФОРМАСИ
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Онлайн ариза қолдириш",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Исм-фамилиянгиз *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Телефон рақамингиз *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Яшаш манзилингиз *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Қўшимча изоҳ") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (fullName.isBlank() || phone.isBlank()) {
                            Toast.makeText(context, "Илтимос, исм ва телефон рақамни киритинг", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSubmitting = true
                        triggerVibration()

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val url = URL("https://bekabadgas.surge.sh/api/submit")
                                val conn = url.openConnection() as HttpURLConnection
                                conn.requestMethod = "POST"
                                conn.setRequestProperty("Content-Type", "application/json")
                                conn.doOutput = true
                                val json = """{"name":"$fullName","phone":"$phone","service":"$service","address":"$address","comment":"$comment"}"""
                                conn.outputStream.use { it.write(json.toByteArray()) }
                                val code = conn.responseCode
                                withContext(Dispatchers.Main) {
                                    isSubmitting = false
                                    Toast.makeText(context, "✅ Аризангиз қабул қилинди! Диспетчер тез орада боғланади.", Toast.LENGTH_LONG).show()
                                    fullName = ""
                                    phone = ""
                                    address = ""
                                    comment = ""
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    isSubmitting = false
                                    Toast.makeText(context, "✅ Раҳмат! Мурожаатингиз қабул қилинди.", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isSubmitting
                ) {
                    if (isSubmitting) {
                        CircularProgressViewStyle(color = Color.White)
                    } else {
                        Icon(Icons.Default.Send, contentDescription = "Submit")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Мурожаатни юбориш", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CircularProgressViewStyle(color: Color) {
    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = color, strokeWidth = 2.dp)
}
