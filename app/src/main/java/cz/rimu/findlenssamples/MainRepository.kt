package cz.rimu.findlenssamples

interface MainRepository {
    fun getPhotos(lensName: String): Any
}