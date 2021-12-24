package com.example.aka102.model

class User {


    private var username: String = ""
    private var fullname: String = ""
    private var bio: String = ""
    private var image: String = ""
    private var latarimage: String = ""
    private var uid: String = ""
    private var status: String = ""
    private var datechat: String = ""
    private var timechat: String = ""

    constructor()


    constructor(username: String, fullname: String, bio: String, image: String, latarimage: String, uid: String, status: String, datechat: String, timechat: String)

    {
        this.username = username
        this.fullname = fullname
        this.bio = bio
        this.image = image
        this.latarimage = latarimage
        this.uid = uid
        this.status = status
        this.datechat = datechat
        this.timechat = timechat


    }

    fun getUsername():String
    {
        return  username
    }

    fun setUsername(username: String)
    {
        this.username = username
    }


    fun getFullname():String
    {
        return  fullname
    }

    fun setFullname(fullname: String)
    {
        this.fullname = fullname
    }


    fun getBio():String
    {
        return  bio
    }

    fun setBio(bio: String)
    {
        this.bio = bio
    }

    fun getImage():String
    {
        return  image
    }

    fun setImage(image: String)
    {
        this.image = image
    }

    fun getLatarimage():String
    {
        return  latarimage
    }

    fun setLatarimage(latarimage: String)
    {
        this.latarimage = latarimage
    }


    fun getUID():String
    {
        return  uid
    }

    fun setUID(uid: String)
    {
        this.uid = uid
    }


    fun getStatus():String
    {
        return  status
    }

    fun setStatus(status: String)
    {
        this.status = status
    }



    fun getDatechat():String
    {
        return  datechat
    }

    fun setDatechat(datechat: String)
    {
        this.datechat = datechat
    }


    fun getTimechat():String
    {
        return  timechat
    }

    fun setTimechat(timechat: String)
    {
        this.timechat = timechat
    }




}