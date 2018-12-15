object Conf {

    const val buildTools = "28.0.3"
    const val targetSdk = 28
    const val minSdk = 21
    val versionCode get() = commitCount()
    const val versionName = "1.0.0"
    const val applicationId = "de.janniskilian.basket"

    private fun commitCount(): Int {
        val p = Runtime.getRuntime().exec("git rev-list --all --count")

        val result = p.waitFor()
        return if (result == 0) {
            p.inputStream.reader().use {
                it.readLines().get(0).toInt()
            }
        } else {
            0
        }
    }
}
