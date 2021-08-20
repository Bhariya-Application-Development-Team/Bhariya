package com.sudhir.bhariya.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.data.model.User
import com.sudhir.bhariya.R
import com.sudhir.bhariya.entity.Feedback

class FeedbackAdapter(val c: Context, val userList:ArrayList<Feedback>):RecyclerView.Adapter<FeedbackAdapter.UserViewHolder>()
{



    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var text:TextView
        var name:TextView
        var mMenus: ImageView

        init {
            text = v.findViewById<TextView>(R.id.mTitle)
            name = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val text = v.findViewById<EditText>(R.id.etfeedback)
                        val name = v.findViewById<EditText>(R.id.etusername)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.username = name.text.toString()
                                position.feedback = text.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c,"Feedback Edited", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Feedback")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Feedback", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.username
        holder.text.text = newList.feedback
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

}
//
//class FeedbackAdapter(private val feedbackList : ArrayList<Feedback>) : RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
//            parent,false)
//        return MyViewHolder(itemView)
//
//    }
//
////    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
////
////        val currentitem = feedbackList[position]
////
////        holder.username.text = currentitem.username
////        holder.feedback.text = currentitem.feedback
////
////    }
//
//    override fun getItemCount(): Int {
//
//        return feedbackList.size
//    }
//
//
//    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//
//        val username : TextView = itemView.findViewById(R.id.tvusername)
//        val feedback : TextView = itemView.findViewById(R.id.tvfeedback)
//
//    }
//
//}
