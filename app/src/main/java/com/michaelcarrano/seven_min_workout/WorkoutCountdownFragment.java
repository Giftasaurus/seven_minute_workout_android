package com.michaelcarrano.seven_min_workout;

import android.content.Context;
import android.media.MediaPlayer;
import android.animation.LayoutTransition;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.Button;
=======
import android.widget.CheckBox;
import android.widget.EditText;
>>>>>>> workout-log
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michaelcarrano.seven_min_workout.data.ExerciseStats;
import com.michaelcarrano.seven_min_workout.data.RepExercise;
import com.michaelcarrano.seven_min_workout.data.Stats;
import com.michaelcarrano.seven_min_workout.data.TimeExercise;
import com.michaelcarrano.seven_min_workout.data.WorkoutContent;
import com.michaelcarrano.seven_min_workout.widget.CircularProgressBar;

/**
 * Created by michaelcarrano on 12/6/13.
 */
public class WorkoutCountdownFragment extends Fragment {
    private boolean isPaused = false;
    private boolean isResting = true;
    private int secondOnTimer = 0;
    private static float REMAINING_TIME;          // Time remaining (ie: device rotation)
    /**
     * The time spent for each activity (exercise or rest)
     */
//    private final int EXERCISE_TIME = 30000;    // 30 seconds
    private final int EXERCISE_TIME = 10000;
    private final int REST_TIME = 10000;        // 10 seconds

    /**
     * Keeps track of the current workout
     */
    private WorkoutContent.Workout mWorkout;

    /**
     * Keeps track of the workout position (ie: 3/12)
     */
    private int mWorkoutPos = 0;

    /**
     * Keeps track of the current CountDownTimer
     */
    private CountDownTimer mCountDownTimer;

    /**
     * The CircularProgressBar used
     */
    private CircularProgressBar mCircularProgressBar;

    /**
     * Tracks if the workout has started
     */
    private boolean workoutInProgress = false;

    /**
     * to keep track of stats
     */
    private Stats stats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain instance on device rotation
        setRetainInstance(true);

        // Set mWorkout to the first workout
        mWorkout = WorkoutContent.WORKOUTS.get(mWorkoutPos);

<<<<<<< HEAD
=======
        //Initialize stats variable
        stats = new Stats(getActivity());
>>>>>>> workout-log
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater
                .inflate(R.layout.fragment_workout_countdown, container, false);
        mCircularProgressBar = (CircularProgressBar) rootView
                .findViewById(R.id.workout_countdown_time);
//        rootView.setBackgroundColor(Color.parseColor(mWorkout.light));

        // Start off with 10 second rest, then alternate
        if (!workoutInProgress) {
            rest(rootView);
        } else {
            exercise(rootView);
        }
        ((ViewGroup) rootView.findViewById(R.id.workout_countdown_info_container)).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        ((ViewGroup) rootView.findViewById(R.id.countdown)).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        pauseAndPlayButtonSetUp(rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Cancel the countdown
        mCountDownTimer.cancel();
    }

<<<<<<< HEAD
    private void setupCountDownTimer(final int millisInFuture, int countDownInterval, final int progressBarMax) {
        mCircularProgressBar.setMax(progressBarMax / 1000);
        mCountDownTimer = new CountDownTimer(millisInFuture, 10) {
=======
    private LinearLayout statsLayout = null;
    private boolean isRep = false;

    private void rest(final View rootView) {

        TextView name = (TextView) rootView.findViewById(R.id.workout_countdown_name);
        if (!workoutInProgress) {
            name.setText(R.string.get_ready);
        } else {
            name.setText(R.string.rest);

            ExerciseStats prev = stats.getStats()[mWorkoutPos - 1];
            if (prev instanceof TimeExercise)
            {
                statsLayout = (LinearLayout) rootView.findViewById(R.id.timeExerciseStats);
                isRep = false;
            } else if (prev instanceof RepExercise)
            {
                statsLayout = (LinearLayout) rootView.findViewById(R.id.repExerciseStats);
                isRep = true;
            }
            statsLayout.setVisibility(View.VISIBLE);
        }

        TextView id = (TextView) rootView.findViewById(R.id.workout_countdown_id);
        id.setText(mWorkout.id);

        id.setBackgroundColor(mWorkout.dark);
        name.setBackgroundColor(mWorkout.light);

        mCountDownTimer = new CountDownTimer(REST_TIME, 10) {
>>>>>>> workout-log
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isPaused) {
                    if (isResting) {
                        REMAINING_TIME = (millisUntilFinished / 1000.0f);
                        if (REMAINING_TIME < 3.59 && REMAINING_TIME > 3.49) {
                            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.mario_cart_start_sound);
                            mediaPlayer.start();
                        }
                        mCircularProgressBar.setProgress(REMAINING_TIME);
                    } else {
                        REMAINING_TIME = (millisUntilFinished / 1000.0f);

                        mCircularProgressBar.setProgress(REMAINING_TIME);
                    }
                }
<<<<<<< HEAD
=======

//                TextView time = (TextView) rootView.findViewById(R.id.workout_countdown_time);
//                time.setText("" + millisUntilFinished / 1000);
                mCircularProgressBar.setMax(REST_TIME / 1000);
                mCircularProgressBar.setProgress(REMAINING_TIME);

>>>>>>> workout-log
            }

            @Override
            public void onFinish() {
<<<<<<< HEAD
                if (isResting) {
                    workoutInProgress = true;
                    exercise(getView());
                } else {
                    if (++mWorkoutPos < WorkoutContent.WORKOUTS.size()) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.ting);
                        mediaPlayer.start();
                        mediaPlayer.pause();
                        mWorkout = WorkoutContent.WORKOUTS.get(mWorkoutPos);
                        rest(getView());
                    } else {
                        finish(getView());
                    }
=======
                workoutInProgress = true;
                exercise(rootView);

                ExerciseStats exercise = stats.getStats()[mWorkoutPos - 1];
                exercise.incrementWorkoutsCompleted();
                if (isRep)
                {
                    EditText tv = (EditText) rootView.findViewById(R.id.repsCompletedPlainText);
                    RepExercise re = (RepExercise) exercise;
                    int reps = Integer.valueOf(tv.getText().toString());
                    re.setCompletedLastTime(reps);
                    if (reps > re.getPersonalBest()) {
                        re.setPersonalBest(reps);
                    }
                    re.addToTotalReps(reps);
                    re.setPersoanlAvg(re.getTotalReps()/re.getWorkoutsCompleted());
                } else
                {
                    CheckBox cb = (CheckBox) rootView.findViewById(R.id.isCompletedCheckBox);
                    TimeExercise te = (TimeExercise) exercise;
                    if (cb.isChecked()) {
                        te.setTotalCompleted(te.getTotalCompleted() + 1);
                    }
                    te.setCompletedLastTime(cb.isChecked());
                    te.setCompletedPercentage(te.getWorkoutsCompleted()/te.getTotalCompleted());
>>>>>>> workout-log
                }
            }
        };
    }

<<<<<<< HEAD
    private void pauseAndPlayButtonSetUp(View rootView) {
        Button pauseBtn = (Button) rootView.findViewById(R.id.pauseAndPlayBtn);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
=======
    private void exercise(final View rootView) {

        (rootView.findViewById(R.id.repExerciseStats)).setVisibility(View.GONE);
        (rootView.findViewById(R.id.timeExerciseStats)).setVisibility(View.GONE);

        TextView ready = (TextView) rootView.findViewById(R.id.workout_countdown_name);
        ready.setText(mWorkout.name);

        TextView id = (TextView) rootView.findViewById(R.id.workout_countdown_id);
        id.setText(mWorkout.id);

        mCountDownTimer = new CountDownTimer(EXERCISE_TIME, 10) {
>>>>>>> workout-log
            @Override
            public void onClick(View view) {
                Button pauseAndPlayBtn = (Button) view;
                if (isPaused) {
                    isPaused = false;
                    pauseAndPlayBtn.setText("PAUSE");

<<<<<<< HEAD
                    setupCountDownTimer((int) (REMAINING_TIME * 1000), 10, mCircularProgressBar.getmMax() * 1000);
                    mCountDownTimer.start();

                } else {
                    mCountDownTimer.cancel();
                    isPaused = true;
                    pauseAndPlayBtn.setText("PLAY");
                }
            }
        });
    }


    private void rest(final View rootView) {
        isResting = true;
        TextView id = (TextView) rootView.findViewById(R.id.workout_countdown_id);
        id.setText(mWorkout.id);
        id.setBackgroundColor(mWorkout.dark);

        TextView name = (TextView) rootView.findViewById(R.id.workout_countdown_name);
        name.setBackgroundColor(mWorkout.light);
        if (!workoutInProgress) {
            name.setText(R.string.get_ready);
        } else {
            name.setText(R.string.rest);
        }
        REMAINING_TIME = REST_TIME / 1000.0f;
        setupCountDownTimer(REST_TIME, 10, REST_TIME);
        mCountDownTimer.start();
    }
=======
//                TextView time = (TextView) rootView.findViewById(R.id.workout_countdown_time);
//                time.setText("" + millisUntilFinished / 1000);
                mCircularProgressBar.setMax(EXERCISE_TIME / 1000);
                mCircularProgressBar.setProgress(REMAINING_TIME);
            }
>>>>>>> workout-log

    private void exercise(final View rootView) {
        isResting = false;
        TextView ready = (TextView) rootView.findViewById(R.id.workout_countdown_name);
        ready.setText(mWorkout.name);

        TextView id = (TextView) rootView.findViewById(R.id.workout_countdown_id);
        id.setText(mWorkout.id);
        REMAINING_TIME = EXERCISE_TIME / 1000.0f;
        setupCountDownTimer(EXERCISE_TIME, 10, EXERCISE_TIME);
        mCountDownTimer.start();
    }

    private void finish(View rootView) {
        mCountDownTimer.cancel();

        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.cheer3);
        mediaPlayer.start();

        // hide the current views
        LinearLayout info = (LinearLayout) rootView.findViewById(R.id.workout_countdown_info);
        info.setVisibility(View.GONE);
        mCircularProgressBar.setVisibility(View.GONE);

        // display "finished"
        TextView textView = (TextView) rootView.findViewById(R.id.workout_countdown_finished);
        textView.setVisibility(View.VISIBLE);
    }

}
