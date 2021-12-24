package com.example.aka102.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aka102.adapter.SearchAdapter
import com.example.aka102.databinding.FragmentSearchBinding
import com.example.aka102.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class SearchFragment : Fragment() {

    private var useradaptersearch: SearchAdapter? = null
    private var mUser: MutableList<User>? = null


    private var _biding: FragmentSearchBinding? = null
    private val biding: FragmentSearchBinding get() = _biding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _biding = FragmentSearchBinding.inflate(inflater, container, false)


        biding.recySearch?.setHasFixedSize(true)
        biding.recySearch?.layoutManager = LinearLayoutManager(context)

        mUser = ArrayList()
        useradaptersearch = context?.let { SearchAdapter(it, mUser as ArrayList<User>, false) }
        biding.recySearch?.adapter = useradaptersearch

        biding.texSearchInstragram.addTextChangedListener(object: TextWatcher

        {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (biding.texSearchInstragram.text.toString() == "")
                {

                }
                else
                {


                    retriveUser()
                    searchUser(s.toString().toLowerCase())

                }
            }

            override fun afterTextChanged(s: Editable?) {
            }


        })




        return biding.root
    }

    private fun searchUser(input: String) {
        val query = FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .orderByChild("username")
            .startAt(input).endAt(input + "\uf8ff")

        query.keepSynced(true)

        query.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot : DataSnapshot) {

                mUser?.clear()


                for (snapshot in dataSnapshot.children)

                {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null)
                    {
                        mUser?.add(user)
                    }

                }

                useradaptersearch?.notifyDataSetChanged()

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })





    }

    private fun retriveUser() {

        val usersRef = FirebaseDatabase.getInstance().getReference().child("Users")
        usersRef.keepSynced(true)
        usersRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot : DataSnapshot) {

                if (biding?.texSearchInstragram?.text.toString() == "")
                {

                    mUser?.clear()

                    for (snapshot in dataSnapshot.children)

                    {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null)
                        {
                            mUser?.add(user)
                        }

                    }

                    useradaptersearch?.notifyDataSetChanged()

                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _biding = null
    }


}
