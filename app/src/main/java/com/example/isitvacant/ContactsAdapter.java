package com.example.isitvacant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends FirestoreRecyclerAdapter<Contacts, ContactsAdapter.ContactsHolder> {
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ContactsAdapter(@NonNull FirestoreRecyclerOptions<Contacts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ContactsHolder holder, int position, @NonNull Contacts model) {


        holder.userName.setText(model.getName());
        holder.userStatus.setText(model.getStatus());
        //holder.userProfileImage.setImageDrawable(model.getImage()););
        Picasso.get().load(model.getImage()).into(holder.userProfileImage);



    }

    @NonNull
    @Override
    public ContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_cards_layout, parent, false);
        return new ContactsHolder(view);
    }

    class ContactsHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView userStatus;
        CircleImageView userProfileImage;

        public ContactsHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.user_status_textview);
            userProfileImage = itemView.findViewById(R.id.users_profile_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int user_position = getAdapterPosition();
                    if (user_position!= RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.OnItemClick(getSnapshots().getSnapshot(user_position),user_position);

                    }

                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }
}
