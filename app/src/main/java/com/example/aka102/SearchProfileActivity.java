package com.example.aka102;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.aka102.databinding.ActivitySearchProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SearchProfileActivity extends AppCompatActivity {

    private ActivitySearchProfileBinding binding;

    private String receiverUserID, senderUserID, current_stste;
    private DatabaseReference UserRef, ChatRequestRef, ContactsRef, NotificationRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.ro);

        mAuth =  FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Permintaan");
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notification");
        UserRef.keepSynced(true);
        ChatRequestRef.keepSynced(true);
        ContactsRef.keepSynced(true);
        NotificationRef.keepSynced(true);
        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();
        senderUserID = mAuth.getCurrentUser().getUid();



        current_stste = "new";

        RetriveUserInfo();


    }


    private void RetriveUserInfo() {

        UserRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("image")))
                {
                    String userImage = dataSnapshot.child("image").getValue().toString();
                    String coverImage = dataSnapshot.child("latarimage").getValue().toString();
                    String userName = dataSnapshot.child("username").getValue().toString();
                    String userStatus = dataSnapshot.child("bio").getValue().toString();


                    try {

                        Glide
                                .with(SearchProfileActivity.this)
                                .load(userImage)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(binding.avatarView);

                        Glide
                                .with(SearchProfileActivity.this)
                                .load(coverImage)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(binding.coverFoto);
                    }
                    catch (Exception e){

                    }



                    binding.fullnameProfile.setText(userName);
                    binding.bioProfile.setText(userStatus);

                    ManageChatRequest();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ManageChatRequest() {

        ChatRequestRef.child(senderUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(receiverUserID))
                        {
                            String request_type = dataSnapshot.child(receiverUserID).child("request_type").getValue().toString();


                            if (request_type.equals("sent"))
                            {
                                current_stste = "request_sent";
                                binding.sendMessageRequest.setText("Batalkan");
                            }

                            else if (request_type.equals("received"))
                            {
                                current_stste = "request_received";
                                binding.sendMessageRequest.setText("Terima");

                                binding.declineMessageRequest.setVisibility(View.VISIBLE);
                                binding.declineMessageRequest.setEnabled(true);

                                binding.declineMessageRequest.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        CancleChatRequest();

                                    }
                                });
                            }

                        }
                        else {

                            ContactsRef.child(senderUserID)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.hasChild(receiverUserID))
                                            {
                                                current_stste = "friends";
                                                binding.sendMessageRequest.setText("Hapus Teman");
                                            }



                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        if (!senderUserID.equals(receiverUserID))
        {
            binding.sendMessageRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.sendMessageRequest.setEnabled(false);

                    if (current_stste.equals("new"))
                    {
                        SendChatRequest();
                    }

                    if (current_stste.equals("request_sent"))
                    {
                        CancleChatRequest();
                    }

                    if (current_stste.equals("request_received"))
                    {
                        AcceptChatRequest();
                    }

                    if (current_stste.equals("friends"))
                    {
                        RemoveSpecificContact();
                    }


                }
            });

        } else {
            binding.sendMessageRequest.setVisibility(View.INVISIBLE);
        }


    }



    private void RemoveSpecificContact() {

        ContactsRef.child(senderUserID).child(receiverUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            ContactsRef.child(receiverUserID).child(senderUserID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                                binding.sendMessageRequest.setEnabled(true);
                                                current_stste = "new";
                                                binding.sendMessageRequest.setText("Ikuti");


                                                binding.declineMessageRequest.setVisibility(View.INVISIBLE);
                                                binding.declineMessageRequest.setEnabled(false);
                                            }

                                        }
                                    });

                        }

                    }
                });

    }


    private void AcceptChatRequest() {

        ContactsRef.child(senderUserID).child(receiverUserID)
                .child("Contacts").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {

                            ContactsRef.child(receiverUserID).child(senderUserID)
                                    .child("Contacts").setValue("Saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {

                                                ChatRequestRef.child(senderUserID).child(receiverUserID)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful())
                                                                {
                                                                    ChatRequestRef.child(receiverUserID).child(senderUserID)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                    binding.sendMessageRequest.setEnabled(true);
                                                                                    current_stste = "friends";
                                                                                    binding.sendMessageRequest.setText("Hapus Teman");

                                                                                    binding.declineMessageRequest.setVisibility(View.INVISIBLE);
                                                                                    binding.declineMessageRequest.setEnabled(false);

                                                                                }
                                                                            });


                                                                }


                                                            }
                                                        });

                                            }


                                        }
                                    });


                        }


                    }
                });


    }

    private void CancleChatRequest() {

        ChatRequestRef.child(senderUserID).child(receiverUserID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            ChatRequestRef.child(receiverUserID).child(senderUserID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                                binding.sendMessageRequest.setEnabled(true);
                                                current_stste = "new";
                                                binding.sendMessageRequest.setText("Ikuti");


                                                binding.declineMessageRequest.setVisibility(View.INVISIBLE);
                                                binding.declineMessageRequest.setEnabled(false);
                                            }

                                        }
                                    });

                        }

                    }
                });


    }

    private void SendChatRequest() {

        ChatRequestRef.child(senderUserID).child(receiverUserID)
                .child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            ChatRequestRef.child(receiverUserID).child(senderUserID)
                                    .child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            HashMap<String, String> chatNotificationMap = new HashMap<>();
                                            chatNotificationMap.put("from", senderUserID);
                                            chatNotificationMap.put("tyoe", "request");

                                            NotificationRef.child(receiverUserID).push()
                                                    .setValue(chatNotificationMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful())
                                                            {

                                                                binding.sendMessageRequest.setEnabled(true);
                                                                current_stste = "request_sent";
                                                                binding.sendMessageRequest.setText("Batalkan");


                                                            }

                                                        }
                                                    });


                                        }
                                    });
                        }

                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}