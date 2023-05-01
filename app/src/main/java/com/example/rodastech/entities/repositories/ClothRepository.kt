package com.example.rodastech.entities.repositories

import com.example.rodastech.entities.Cloth

class ClothRepository() {
    var cloths: MutableList<Cloth> = mutableListOf()

    init {
        cloths.add(Cloth(1, "Jean Azul Oscuro", "1200 Mts", 100))
        cloths.add(Cloth(2, "Jean Gris", "600 Mts", 120))
        cloths.add(Cloth(3, "Jean Negro", "700 Mts", 160))
        cloths.add(Cloth(4, "Jean Celeste", "1600 Mts", 80))
        cloths.add(Cloth(5, "Jean Azul", "1350 Mts", 140))
        cloths.add(Cloth(6, "Gabardina Negra", "1300 Mts", 70))
        cloths.add(Cloth(7, "Gabardina Marrón", "1100 Mts", 60))
        cloths.add(Cloth(8, "Jean Blanco", "1800 Mts", 100))
        cloths.add(Cloth(9, "Jean Oscuro", "1200 Mts", 100))
        cloths.add(Cloth(10, "Jean Oscuro", "1200 Mts", 100))
    }
}