package br.com.freedomgist


import org.koin.dsl.module

sealed class Flavours(name: String) {
    class Blue(val foo : String) : Flavours(Blue::javaClass.name){
        override fun toString(): String = "Blue $foo"

    }
    class Red(val bar : String) : Flavours(Red::javaClass.name){
        override fun toString(): String = "Blue $bar"
    }

    companion object{
        fun fromFlavourName(name : String) = when(name){
            "Blue" -> Blue("foo")
            "Red" -> Red("bar")
            else -> throw Exception("Invalid Flavour : $name")
        }
    }
}

val flavourModules = module {
    single { Flavours.fromFlavourName(BuildConfig.FLAVOR) }
}