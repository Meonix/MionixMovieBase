package com.nice.myapplication.model

class PopularMovie {
    var id : Int =0
    var title: String =""
    var poster : String =""
    var overview: String =""
    var datesave: String =""
    constructor(id: Int,
                title: String,
                poster: String,
                overview: String,
                datesave:String) {
        this.id = id
        this.title = title
        this.poster = poster
        this.overview = overview
        this.datesave = datesave
    }
    constructor() {
    }
}