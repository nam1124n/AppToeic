package com.example.toeic_app.domain.model

enum class PartType(val title: String, val description: String) {
    PART_1("Photographs", "Four short statements regarding a photograph will be spoken only one time. Select the one that best describes the photograph."),
    PART_2("Question-Response", "You will hear a question or statement and three responses spoken in English. Select the best response to the question or statement."),
    PART_3("Conversations", "You will hear some conversations between two or more people. You will be asked to answer three questions about what the speakers say in each conversation."),
    PART_4("Talks", "You will hear some talks given by a single speaker. You will be asked to answer three questions about what the speaker says in each talk."),
    PART_5("Incomplete Sentences", "A word or phrase is missing in each of the sentences below. Four answer choices are given below each sentence. Select the best answer to complete the sentence."),
    PART_6("Text Completion", "Read the texts associated with each question. A word, phrase, or sentence is missing in parts of each text. Four answer choices are given below the text. Select the best answer to complete the text."),
    PART_7("Reading Comprehension", "In this part, you will read a selection of texts, such as magazine and newspaper articles, e-mails, and instant messages. Each text or set of texts is followed by several questions. Select the best answer for each question.")
}
