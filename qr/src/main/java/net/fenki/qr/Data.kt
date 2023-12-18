package net.fenki.qr

object Data {
    private var persistentValue: String = "<Нет значения>"

    fun get(): String {
        return persistentValue
    }

    fun set(newValue: String) {
        persistentValue =  newValue.takeIf {  it.isNotEmpty() }?:"<Нет значения>";
    }
}