package com.example.bookify.homenav.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookify.Book;
import com.example.bookify.BookView;
import com.example.bookify.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchFragment extends Fragment {

    private ImageButton mSearchButton;
    private EditText mSearchText;
    private Button mSearchBack;

    private RecyclerView mResultList;
    private DatabaseReference mSearchDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchDatabase = FirebaseDatabase.getInstance().getReference("books");

        mSearchText = (EditText) root.findViewById(R.id.searchText);
        mSearchButton = (ImageButton) root.findViewById(R.id.searchButton);
        mSearchBack = (Button) root.findViewById(R.id.searchClear);

        mResultList = (RecyclerView) root.findViewById(R.id.resultList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchInput = mSearchText.getText().toString();
                firebaseSearch(searchInput);
            }
        });

        mSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput = mSearchText.getText().toString();
                if (textInput.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Text is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mSearchText.setText("");
                }
            }
        });
        return root;
    }



    private void firebaseSearch(String searchInput){

        Query firebaseSearchQuery = mSearchDatabase.orderByChild("title").startAt(searchInput).endAt(searchInput + "\uf8ff");
        final FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                    .setQuery(firebaseSearchQuery, Book.class)
                    .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Book, SearchViewHolder>(options) {
            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_view, parent,false);

                return new SearchViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, final int position, @NonNull final Book model) {
                holder.setDetails(getActivity().getApplicationContext(),model.getTitle(),model.getCover_url(),model.getAuthor());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), BookView.class);
                        intent.putExtra("Book", model);
                        getContext().startActivity(intent);

                    }
                });
            }
        };

        mResultList.setAdapter(adapter);
        adapter.startListening();
    }

    // View Holder Class

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String showTitle, String showCover, String showAuthor){

            TextView show_title = (TextView) mView.findViewById(R.id.searchTitle);
            TextView show_author = (TextView) mView.findViewById(R.id.searchAuthor);
            ImageView show_cover = (ImageView) mView.findViewById(R.id.searchCover);

            show_title.setText(showTitle);
            show_author.setText(showAuthor);

            Glide.with(ctx).load(showCover).into(show_cover);
        }

    }
}