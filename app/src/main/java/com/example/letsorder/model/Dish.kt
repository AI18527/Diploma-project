package com.example.letsorder.model

data class Dish(val id:Int, val title:String, val description:String, val prize:Double)
{
    constructor() : this(0,"title", "description", 0.0)
}
