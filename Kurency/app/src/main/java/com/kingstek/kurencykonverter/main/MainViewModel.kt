package com.kingstek.kurencykonverter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingstek.kurencykonverter.data.models.Rates
import com.kingstek.kurencykonverter.util.DispatcherProvider
import com.kingstek.kurencykonverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class CurrencyEvent {
        class Success (val resultText: String) : CurrencyEvent()
        class Failure (val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert (
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {

        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid number")
            return
        }

        viewModelScope.launch (dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesRespnse = repository.getRates(fromCurrency)) {
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesRespnse.message!!)
                is Resource.Success -> {
                    val rates = ratesRespnse.data!!.rates
                    val rate = getRateForCurrency(toCurrency, rates)
                    if(rate == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "HRK" -> rates.hRK
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR
        else -> null
    }

//    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
//
//        "AED" -> rates.AED
//        "AFN" -> rates.AFN
//        "ALL" -> rates.ALL
//        "AMD" -> rates.AMD
//        "ANG"-> rates.ANG
//        "AOA"-> rates.AOA
//        "ARS"-> rates.ARS
//        "AUD"-> rates.AUD
////             AWG-> Double,
////             AZN: Double,
////             BAM: Double,
////             BBD: Double,
////             BDT: Double,
////             BGN: Double,
////             BHD: Double,
////             BIF: Double,
////             BMD: Double,
////             BND: Double,
////             BOB: Double,
////             BRL: Double,
////             BSD: Double,
////             BTC: Double,
////             BTN: Double,
////             BWP: Double,
////             BYN: Double,
////             BYR: Double,
////             BZD: Double,
//             "CAD"-> rates.CAD
////             CDF: Double,
////             CHF: Double,
////             CLF: Double,
////             CLP: Double,
////             CNY: Double,
////             COP: Double,
////             CRC: Double,
////             CUC: Double,
////             CUP: Double,
////             CVE: Double,
////             CZK: Double,
////             DJF: Double,
////             DKK: Double,
////             DOP: Double,
////             DZD: Double,
////             EGP: Double,
////             ERN: Double,
////             ETB: Double,
//             "EUR"->  rates.EUR
////             FJD: Double,
////             FKP: Double,
////             GBP: Double,
////             GEL: Double,
////             GGP: Double,
////             GHS: Double,
////             GIP: Double,
////             GMD: Double,
////             GNF: Double,
////             GTQ: Double,
////             GYD: Double,
////             HKD: Double,
////             HNL: Double,
////             HRK: Double,
////             HTG: Double,
////             HUF: Double,
////             IDR: Double,
////             ILS: Double,
////             IMP: Double,
////             INR: Double,
////             IQD: Double,
////             IRR: Double,
////             ISK: Double,
////             JEP: Double,
////             JMD: Double,
////             JOD: Double,
////             JPY: Double,
////             KES: Double,
////             KGS: Double,
////             KHR: Double,
////             KMF: Double,
////             KPW: Double,
////             KRW: Double,
////             KWD: Double,
////             KYD: Double,
////             KZT: Double,
////             LAK: Double,
////             LBP: Double,
////             LKR: Double,
////             LRD: Double,
////             LSL: Double,
////             LTL: Double,
////             LVL: Double,
////             LYD: Double,
////             MAD: Double,
////             MDL: Double,
////             MGA: Double,
////             MKD: Double,
////             MMK: Double,
////             MNT: Double,
////             MOP: Double,
////             MRO: Double,
////             MUR: Double,
////             MVR: Double,
////            val MWK: Double,
////            val MXN: Double,
////            val MYR: Double,
////            val MZN: Double,
////            val NAD: Double,
////            val NGN: Double,
////            val NIO: Double,
////            val NOK: Double,
////            val NPR: Double,
////            val NZD: Double,
////            val OMR: Double,
////            val PAB: Double,
////            val PEN: Double,
////            val PGK: Double,
////            val PHP: Double,
////            val PKR: Double,
////            val PLN: Double,
////            val PYG: Double,
////            val QAR: Double,
////            val RON: Double,
////            val RSD: Double,
////            val RUB: Double,
////            val RWF: Double,
////            val SAR: Double,
////            val SBD: Double,
////            val SCR: Double,
////            val SDG: Double,
////            val SEK: Double,
////            val SGD: Double,
////            val SHP: Double,
////            val SLL: Double,
////            val SOS: Double,
////            val SRD: Double,
////            val STD: Double,
////            val SVC: Double,
////            val SYP: Double,
////            val SZL: Double,
////            val THB: Double,
////            val TJS: Double,
////            val TMT: Double,
////            val TND: Double,
////            val TOP: Double,
////            val TRY: Double,
////            val TTD: Double,
////            val TWD: Double,
////            val TZS: Double,
////            val UAH: Double,
////            val UGX: Double,
////            val USD: Double,
////            val UYU: Double,
////            val UZS: Double,
////            val VEF: Double,
////            val VND: Double,
////            val VUV: Double,
////            val WST: Double,
////            val XAF: Double,
////            val XAG: Double,
////            val XAU: Double,
////            val XCD: Double,
////            val XDR: Double,
////            val XOF: Double,
////            val XPF: Double,
////            val YER: Double,
////            val ZAR: Double,
////            val ZMK: Double,
////            val ZMW: Double,
////            val ZWL: Double
////            val AED: Double,
////            val AFN: Double,
////            val ALL: Double,
////            val AMD: Double,
////            val ANG: Double,
////            val AOA: Double,
////            val ARS: Double,
////            val AUD: Double,
////            val AWG: Double,
////            val AZN: Double,
////            val BAM: Double,
////            val BBD: Double,
////            val BDT: Double,
////            val BGN: Double,
////            val BHD: Double,
////            val BIF: Double,
////            val BMD: Double,
////            val BND: Double,
////            val BOB: Double,
////            val BRL: Double,
////            val BSD: Double,
////            val BTC: Double,
////            val BTN: Double,
////            val BWP: Double,
////            val BYN: Double,
////            val BYR: Double,
////            val BZD: Double,
////            val CAD: Double,
////            val CDF: Double,
////            val CHF: Double,
////            val CLF: Double,
////            val CLP: Double,
////            val CNY: Double,
////            val COP: Double,
////            val CRC: Double,
////            val CUC: Double,
////            val CUP: Double,
////            val CVE: Double,
////            val CZK: Double,
////            val DJF: Double,
////            val DKK: Double,
////            val DOP: Double,
////            val DZD: Double,
////            val EGP: Double,
////            val ERN: Double,
////            val ETB: Double,
////            val EUR: Int,
////            val FJD: Double,
////            val FKP: Double,
////            val GBP: Double,
////            val GEL: Double,
////            val GGP: Double,
////            val GHS: Double,
////            val GIP: Double,
////            val GMD: Double,
////            val GNF: Double,
////            val GTQ: Double,
////            val GYD: Double,
////            val HKD: Double,
////            val HNL: Double,
////            val HRK: Double,
////            val HTG: Double,
////            val HUF: Double,
////            val IDR: Double,
////            val ILS: Double,
////            val IMP: Double,
////            val INR: Double,
////            val IQD: Double,
////            val IRR: Double,
////            val ISK: Double,
////            val JEP: Double,
////            val JMD: Double,
////            val JOD: Double,
////            val JPY: Double,
////            val KES: Double,
////            val KGS: Double,
////            val KHR: Double,
////            val KMF: Double,
////            val KPW: Double,
////            val KRW: Double,
////            val KWD: Double,
////            val KYD: Double,
////            val KZT: Double,
////            val LAK: Double,
////            val LBP: Double,
////            val LKR: Double,
////            val LRD: Double,
////            val LSL: Double,
////            val LTL: Double,
////            val LVL: Double,
////            val LYD: Double,
////            val MAD: Double,
////            val MDL: Double,
////            val MGA: Double,
////            val MKD: Double,
////            val MMK: Double,
////            val MNT: Double,
////            val MOP: Double,
////            val MRO: Double,
////            val MUR: Double,
////            val MVR: Double,
////            val MWK: Double,
////            val MXN: Double,
////            val MYR: Double,
////            val MZN: Double,
////            val NAD: Double,
////            val NGN: Double,
////            val NIO: Double,
////            val NOK: Double,
////            val NPR: Double,
////            val NZD: Double,
////            val OMR: Double,
////            val PAB: Double,
////            val PEN: Double,
////            val PGK: Double,
////            val PHP: Double,
////            val PKR: Double,
////            val PLN: Double,
////            val PYG: Double,
////            val QAR: Double,
////            val RON: Double,
////            val RSD: Double,
////            val RUB: Double,
////            val RWF: Double,
////            val SAR: Double,
////            val SBD: Double,
////            val SCR: Double,
////            val SDG: Double,
////            val SEK: Double,
////            val SGD: Double,
////            val SHP: Double,
////            val SLL: Double,
////            val SOS: Double,
////            val SRD: Double,
////            val STD: Double,
////            val SVC: Double,
////            val SYP: Double,
////            val SZL: Double,
////            val THB: Double,
////            val TJS: Double,
////            val TMT: Double,
////            val TND: Double,
////            val TOP: Double,
////            val TRY: Double,
////            val TTD: Double,
////            val TWD: Double,
////            val TZS: Double,
////            val UAH: Double,
////            val UGX: Double,
////            val USD: Double,
////            val UYU: Double,
////            val UZS: Double,
////            val VEF: Double,
////            val VND: Double,
////            val VUV: Double,
////            val WST: Double,
////            val XAF: Double,
////            val XAG: Double,
////            val XAU: Double,
////            val XCD: Double,
////            val XDR: Double,
////            val XOF: Double,
////            val XPF: Double,
////            val YER: Double,
////            val ZAR: Double,
////            val ZMK: Double,
////            val ZMW: Double,
////            val ZWL: Double
//        else -> null
//    }
}
