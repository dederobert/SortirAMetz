package a1819.m2ihm.sortirametz.models

enum class Gender(var code:Int) {
    MAN(1),
    WOMAN(2);

    companion object {
        fun fromCode(code:Int): Gender? {
            Gender.values().forEach { if (it.code == code) return it }
            return null
        }

        fun fromCode(code:String): Gender? {
            return fromCode(Integer.parseInt(code))
        }
    }
}