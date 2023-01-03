package com.wiapp.imccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiapp.imccalculator.ui.theme.ImcCalculatorTheme
import com.wiapp.imccalculator.ui.theme.Shapes
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImcCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Body()
                }
            }
        }
    }
}


@Composable
fun Body() {
    var nameForTextField by remember { mutableStateOf("") }
    var ageForTextField  by remember { mutableStateOf("") }
    var manIsCheck by remember { mutableStateOf(true) }
    var weigth by remember { mutableStateOf(0) }
    var valueHeigth by remember { mutableStateOf(40.0F) }
    var activitySelectedIndex by remember { mutableStateOf(0) }
    var txtResultCalcul by remember { mutableStateOf("") }

    val manager = LocalFocusManager.current
    val listSelectActivity = listOf<String>("Sédentaire","Faible","Actif","Sportif","Athlete")
    val listGender = listOf<String>("Homme","Femme")
    val modifierRow = Modifier
        .fillMaxWidth()
        .padding(15.dp)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutLinedTextFieldName(valueTextFieldName = nameForTextField , onChangedTextFieldName = {nameForTextField = it} )
        OutLinedTextFieldAge(valueTextFieldAge = ageForTextField, onChangedTextFieldAge = {ageForTextField = it}, manager = manager)


        Row(modifier = modifierRow,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (manIsCheck == true){
                Text(text = listGender[0])
            }else{
                Text(text = listGender[1])
            }
            SwitchCompose(isCheck = manIsCheck, onChangedCheck = {manIsCheck = it})
        }
        StepperWeigth(weigth = weigth, onChengedWeigthValue = {weigth = it}, modifier = modifierRow)

        Text(text = "Taille: ${valueHeigth.toInt()} cm")
        SliderHeigth(valueHeigth = valueHeigth, onChangedHeigthValue = {valueHeigth = it})

        SelectActivity(index = activitySelectedIndex, list = listSelectActivity, onClick = { activitySelectedIndex = it})

        CalculateButton()
        
        Text(text = "${calculateImc(weigth, valueHeigth)}")
        Text(text = "${calculateKcal(weigth,valueHeigth,ageForTextField,manIsCheck,activitySelectedIndex)}")

        }
    }


@Composable
fun OutLinedTextFieldName(valueTextFieldName: String,onChangedTextFieldName: (String) -> Unit){
    OutlinedTextField(
        label = { Text(text = "Prénom")},
        value = valueTextFieldName,
        onValueChange = onChangedTextFieldName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        shape = Shapes.small,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
}

@Composable
fun OutLinedTextFieldAge(valueTextFieldAge: String, manager: FocusManager, onChangedTextFieldAge: (String) -> Unit){
    OutlinedTextField(
        label = { Text(text = "Age")},
        value = valueTextFieldAge ,
        onValueChange = onChangedTextFieldAge,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { manager.clearFocus() }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        shape = Shapes.small,
    )
}

@Composable
fun SwitchCompose(isCheck: Boolean, onChangedCheck: (Boolean) -> Unit){
    Switch(
        checked = isCheck, 
        onCheckedChange = onChangedCheck
    )
}

@Composable
fun StepperWeigth(weigth: Int, modifier: Modifier,onChengedWeigthValue: (Int) -> Unit){
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$weigth Kg")

            Surface(
                modifier = Modifier
                    .height(35.dp)
                    .width(120.dp),
                border = BorderStroke(2.dp, Color.Magenta),
                shape = RoundedCornerShape(percent = 30),

            ) {
                Row() {
                    TextButton(onClick = { onChengedWeigthValue(weigth + 1) }) {
                        Text(text = "+")
                    }
                    TextButton(onClick = { onChengedWeigthValue(weigth - 1) }) {
                        Text(text = "-")
                    }
                }
            }
        }

    }

@Composable
fun SliderHeigth(valueHeigth: Float, onChangedHeigthValue: (Float) -> Unit){
    Slider(
        value = valueHeigth ,
        onValueChange = onChangedHeigthValue,
        valueRange = 40.0F..272.0F
    )
}

@Composable
fun SelectActivity(index: Int,list: List<String>, onClick: (Int) -> Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
    ) {
        for (listSelectActivity in list.indices){
            Column() {
                RadioButton(
                    selected = index == listSelectActivity,
                    onClick = { onClick(listSelectActivity) }
                )
                Text(
                    text = list[listSelectActivity],
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CalculateButton() {
    Button(
        onClick = {}
    ) {
        Text(text = "Calculer")
    }
}

fun calculateImc(weigth: Int,valueHeigth: Float): Double {
    val valueHeigthToMeter = valueHeigth/ 100
    val formulaCalculateImc = weigth / (valueHeigthToMeter * valueHeigthToMeter)

    return formulaCalculateImc.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
}


fun coeff(activitySelectedIndex: Int): Double {

    return when (activitySelectedIndex){
        0 -> 1.2
        1 -> 1.375
        2 -> 1.55
        3 -> 1.7
        else -> 1.9
    }
}

fun calculateKcal(
    weigth: Int,
    valueHeigth: Float, ageForTextField: String,
    manIsCheck: Boolean,
    activitySelectedIndex: Int
): String {

    if (ageForTextField != ""){
        val formulaCalculateKcal = (10 * weigth) + (6.25 * valueHeigth) - (5 * ageForTextField.toInt())

        val caloriesGender = if (manIsCheck) {
            formulaCalculateKcal + 5

        } else {
            formulaCalculateKcal - 161
        }
        val calculKcalWithGender = caloriesGender * coeff(activitySelectedIndex)
        val resultCalculKcal = "Vous devez manger ${calculKcalWithGender.toInt()} Kcal par jours"
        return resultCalculKcal
    }

    return "Veillez saisir votre age pour le calcule des Kcal"
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImcCalculatorTheme {
        Body()
    }
}