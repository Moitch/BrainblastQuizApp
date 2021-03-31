# BrainblastQuizApp
WORK IN PROGRESS!!!!!!

This program is a simple quiz app.

(IDEAL)
Upon starting the app, the user logs in. After logging in, they are welcomed back.
Users have the choice to go to their profile, leaderboards, or pick a category of quizzes.

If the user clicks the profile button they are able to see their quizzes answered, questions answered, correct answers, their display name, and when they joined.

If the user clicks the leaderboard button they are able to see the high scores on quizzes, users with the most questions answered, and users with the most correct answers.

If the user clicks the play button it takes them to the recyclerView with the categories of quizzes. After a user picks a category 
it gives them a list of quizzes to choose from.

After the user picks the quiz they want, it starts the quiz. They are multiple choice quizzes so there are 4 options to pick from for each question.
If the user chooses correctly the button turns green and the rest are greyed out. If the user chooses incorrectly it turns red and the correct one turns green.

After the quiz it displays how many the user got correct out of the # of questions and adds the two statistics(questions answered and correct answers) to the users stats.
-------------------------------------------------------------------------------------------------------------------------------
There are currently 5 views.

Main menu, displays a welcome back message with the users display name. ("Welcome back Mitchell")

Profile, has the users display name as well as the account creation.

Leaderboard, displays the top scores on quizzes or most questions answered. (Not working)

Quiz Categories, displays all of the available categories for users to do a quiz on.

Question view, displays the question along with the 4 options to answer with. (Not working, couldn't figure out how to display the questions answers into the recycler view 
since the code I have loops through the entries. Need to figure how to fix this for assignment #3.)
