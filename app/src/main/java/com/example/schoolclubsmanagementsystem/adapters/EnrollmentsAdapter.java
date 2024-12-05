package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.Student;

import java.util.List;

public class EnrollmentsAdapter extends RecyclerView.Adapter<EnrollmentsAdapter.StudentViewHolder> {

    private Context context;
    private List<Student> studentsList;

    public EnrollmentsAdapter(Context context, List<Student> studentsList) {
        this.context = context;
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentsList.get(position);
        holder.studentNameTextView.setText(student.getName());
        holder.studentEmailTextView.setText(student.getEmail());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        TextView studentEmailTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.student_name_text_view);
            studentEmailTextView = itemView.findViewById(R.id.student_email_text_view);
        }
    }
}
