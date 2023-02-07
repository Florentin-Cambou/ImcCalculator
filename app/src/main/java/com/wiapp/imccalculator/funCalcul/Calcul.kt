package com.wiapp.imccalculator.funCalcul

import java.math.RoundingMode

class Calcul {
    fun calculateImc(weightForTextField: String, valueHeight: Float): Double {
        val valueHeigthToMeter = valueHeight / 100
        val formulaCalculateImc = weightForTextField.toInt() / (valueHeigthToMeter * valueHeigthToMeter)

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
        weightForTextField: String,
        valueHeight: Float, ageForTextField: String,
        manIsCheck: Boolean,
        activitySelectedIndex: Int
    ): Double {

        if (ageForTextField != ""){
            val formulaCalculateKcal = (10 * weightForTextField.toInt()) + (6.25 * valueHeight) - (5 * ageForTextField.toInt())

            val caloriesGender = if (manIsCheck) {
                formulaCalculateKcal + 5

            } else {
                formulaCalculateKcal - 161
            }

            return  caloriesGender * coeff(activitySelectedIndex)
        }
        return calculateKcal(weightForTextField, valueHeight, ageForTextField, manIsCheck, activitySelectedIndex)
    }
}