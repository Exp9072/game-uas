import tkinter as tk
import customtkinter
from tkinter import filedialog
from stegano import lsb
from PIL import Image

def encode_text():
    original_image_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif")])
    if original_image_path:
        secret_text = input_box.get('1.0', 'end-1c')
        if secret_text:
            output_image_path = filedialog.asksaveasfilename(defaultextension=".png", filetypes=[("PNG files", "*.png")])
            if output_image_path:
                encode_text_in_image(original_image_path, secret_text, output_image_path)
                result_label.config(text=f"Text encoded successfully in {output_image_path}")
            else:
                result_label.config(text="Operation canceled.")
        else:
            result_label.config(text="Please enter a secret text.")
    else:
        result_label.config(text="Please select an image.")

def decode_text():
    encoded_image_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif")])
    if encoded_image_path:
        decoded_text = decode_text_from_image(encoded_image_path)
        result_label.config(text=f"Decoded Text: {decoded_text}")
    else:
        result_label.config(text="Please select an encoded image.")

def encode_text_in_image(original_image_path, secret_text, output_image_path):
    original_image = Image.open(original_image_path)
    encoded_image = lsb.hide(original_image, secret_text)
    encoded_image.save(output_image_path)

def decode_text_from_image(encoded_image_path):
    encoded_image = Image.open(encoded_image_path)
    decoded_text = lsb.reveal(encoded_image)
    return decoded_text

# Function for theme change
def theme_change():
    global theme
    if theme == 'dark':
        theme = 'light'
    elif theme == 'light':
        theme = 'dark'

    customtkinter.set_appearance_mode(theme)

# Theme setting
theme = 'dark'
customtkinter.set_appearance_mode(theme)
customtkinter.set_default_color_theme('blue')

# Main window
root = customtkinter.CTk()
root.geometry('600x350')
root.title('Steganografi Encode/Decode')
root.resizable(False, False)

# Input Frame
input_frame = customtkinter.CTkFrame(
    root,
    width=600,
    height=380,
    corner_radius=0
)
input_frame.place(x=10, y=10)

# Input Box
input_box = customtkinter.CTkTextbox(
    input_frame,
    width=490,
    height=105,
    corner_radius=0,
    font=customtkinter.CTkFont(size=14),
    wrap='word',
    activate_scrollbars=True,
)
input_box.place(x=85, y=30)

# Encode button
encode_button = customtkinter.CTkButton(
    input_frame,
    text="Encode Text",
    corner_radius=5,
    command=encode_text
)
encode_button.place(x=85, y=144)

# Decode button
decode_button = customtkinter.CTkButton(
    input_frame,
    text="Decode Text",
    corner_radius=5,
    command=decode_text
)
decode_button.place(x=435, y=144)

# Result label
result_label = customtkinter.CTkLabel(
    input_frame,
    text="Input text here :",
    font=customtkinter.CTkFont(size=12),
    wraplength=400
)
result_label.place(x=1, y=30)

# Application
root.mainloop()
