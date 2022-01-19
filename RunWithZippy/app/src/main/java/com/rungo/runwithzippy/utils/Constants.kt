package com.rungo.runwithzippy.utils
import com.google.android.gms.wallet.WalletConstants
object Constants {
    const val API = "http://v2.myrungo.com"
//    const val API = "http://130.193.55.92"

    const val emailAuth = "email"
    const val googleAuth = "google"
    const val facebookAuth = "facebook"

    const val PREFERENCE_NAME = "zippy_prefs"

    //SharedPreferences keys
    const val ACCESS_TOKEN = "access_token"

    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST
    val SUPPORTED_NETWORKS = listOf(
        "AMEX",
        "DISCOVER",
        "JCB",
        "MASTERCARD",
        "VISA")
    val SUPPORTED_METHODS = listOf(
        "PAN_ONLY",
        "CRYPTOGRAM_3DS")
    const val COUNTRY_CODE = "RU"
    const val CURRENCY_CODE = "RUB"

    const val PAYMENT_TYPE_1200_COINS = "com.myrungo.ios.app.1200.coins"
    const val PAYMENT_TYPE_500_COINS = "com.myrungo.ios.app.500.coins"
    const val PAYMENT_TYPE_80_COINS = "com.myrungo.ios.app.80.coins"
    const val PAYMENT_TYPE_ADS = "ads"

    /**
     * Supported countries for shipping (use ISO 3166-1 alpha-2 country codes). Relevant only when
     * requesting a shipping address.
     *
     * @value #SHIPPING_SUPPORTED_COUNTRIES
     */
    val SHIPPING_SUPPORTED_COUNTRIES = listOf("US", "GB")

    /**
     * The name of your payment processor/gateway. Please refer to their documentation for more
     * information.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_NAME
     */
    const val PAYMENT_GATEWAY_TOKENIZATION_NAME = "example"

    /**
     * Custom parameters required by the processor/gateway.
     * In many cases, your processor / gateway will only require a gatewayMerchantId.
     * Please refer to your processor's documentation for more information. The number of parameters
     * required and their names vary depending on the processor.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS
     */
    val PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS = mapOf(
        "gateway" to PAYMENT_GATEWAY_TOKENIZATION_NAME,
        "gatewayMerchantId" to "exampleGatewayMerchantId"
    )

    /**
     * Only used for `DIRECT` tokenization. Can be removed when using `PAYMENT_GATEWAY`
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PUBLIC_KEY
     */
    const val DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME"

    /**
     * Parameters required for `DIRECT` tokenization.
     * Only used for `DIRECT` tokenization. Can be removed when using `PAYMENT_GATEWAY`
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PARAMETERS
     */
    val DIRECT_TOKENIZATION_PARAMETERS = mapOf(
        "protocolVersion" to "ECv1",
        "publicKey" to DIRECT_TOKENIZATION_PUBLIC_KEY
    )
}