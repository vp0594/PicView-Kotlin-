package com.example.picviewkontlin

class AlbumData(private var FolderName: String, private var imagePath: String) {
    fun getFolderName(): String {
        return FolderName
    }

    fun getImagePath(): String {
        return imagePath
    }
}
