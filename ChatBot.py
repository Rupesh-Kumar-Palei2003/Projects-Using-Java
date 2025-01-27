import nltk
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import random
import string

# Download necessary NLTK data
nltk.download("punkt")
nltk.download("wordnet")

# Sample chatbot corpus (can be extended)
chatbot_corpus = """
Hello! How can I assist you today?
I can help you with your queries regarding machine learning, Python programming, or general questions.
Machine learning is a field of artificial intelligence that uses statistical techniques to give computers the ability to learn from data.
Python is a versatile programming language widely used for web development, data analysis, and machine learning.
"""

# Tokenization
sentence_tokens = nltk.sent_tokenize(chatbot_corpus)

# Preprocessing: Lemmatization
def preprocess(text):
    lemmatizer = nltk.WordNetLemmatizer()
    tokens = nltk.word_tokenize(text.lower())
    tokens = [lemmatizer.lemmatize(token) for token in tokens if token not in string.punctuation]
    return ' '.join(tokens)

# Preprocess sentences
processed_sentences = [preprocess(sentence) for sentence in sentence_tokens]

# Chatbot response function
def generate_response(user_input):
    user_input = preprocess(user_input)
    processed_sentences.append(user_input)
    
    # Vectorize the sentences
    vectorizer = CountVectorizer().fit_transform(processed_sentences)
    vectors = vectorizer.toarray()
    
    # Compute cosine similarity
    cosine_sim = cosine_similarity(vectors[-1], vectors[:-1])
    
    # Find best matching response
    best_match_idx = np.argmax(cosine_sim)
    
    if cosine_sim[0][best_match_idx] == 0:
        response = "I'm sorry, I didn't understand that. Can you please rephrase?"
    else:
        response = sentence_tokens[best_match_idx]
    
    processed_sentences.pop()  # Remove the user input from the processed list
    return response

# Chat loop
def chatbot():
    print("Chatbot: Hi! I'm your assistant. Type 'bye' to exit.")
    while True:
        user_input = input("You: ")
        if user_input.lower() == 'bye':
            print("Chatbot: Goodbye! Have a great day!")
            break
        else:
            print(f"Chatbot: {generate_response(user_input)}")

# Start the chatbot
if __name__ == "__main__":
    chatbot()
