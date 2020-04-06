import pyrebase


def get_db():
    config = {
        "apiKey": "AIzaSyBHz-ZrX8ANSYz3qcVdbjQ_KvpX8Kz3PnU",
        "authDomain": "watechpark.firebaseapp.com",
        "databaseURL": "https://watechpark.firebaseio.com",
        "storageBucket": "watechpark.appspot.com"
    }
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()

    return db