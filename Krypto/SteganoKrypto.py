import tkinter as tk
from tkinter import filedialog
from PIL import Image, ImageTk
import customtkinter
from stegano import lsb

def encode_text():
    original_image_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif")])
    if original_image_path:
        img = Image.open(original_image_path)
        img = img.resize((150, 150), Image.ANTIALIAS)
        photo = ImageTk.PhotoImage(img)
        img_label.config(image=photo)
        img_label.image = photo

        secret_text = input_box.get('1.0', 'end-1c')
        if secret_text:
            output_image_path = filedialog.asksaveasfilename(defaultextension=".png", filetypes=[("PNG files", "*.png")])
            if output_image_path:
                encode_text_in_image(original_image_path, secret_text, output_image_path)
                result_label.configure(text=f"Text encoded successfully")
            else:
                result_label.configure(text="Operation canceled.")
        else:
            result_label.configure(text="Please enter a secret text.")
    else:
        result_label.configure(text="Please select an image.")

def decode_text():
    encoded_image_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif")])
    if encoded_image_path:
        img = Image.open(encoded_image_path)
        img = img.resize((150, 150), Image.ANTIALIAS)
        photo = ImageTk.PhotoImage(img)
        img_label.config(image=photo)
        img_label.image = photo
        decoded_text = decode_text_from_image(encoded_image_path)
        result_label.configure(text=f"Decoded Text: {decoded_text}")

    else:
        result_label.configure(text="Please select an encoded image.")

def encode_text_in_image(original_image_path, secret_text, output_image_path):
    original_image = Image.open(original_image_path)
    encoded_image = lsb.hide(original_image, secret_text)
    encoded_image.save(output_image_path)

def decode_text_from_image(encoded_image_path):
    encoded_image = Image.open(encoded_image_path)
    decoded_text = lsb.reveal(encoded_image)
    return decoded_text


# Theme setting
theme = '#427D9D'
customtkinter.set_appearance_mode(theme)
customtkinter.set_default_color_theme('blue')

# Main window
root = customtkinter.CTk()
root.geometry('600x350')
root.title('Steganografi Encode/Decode')
root.config(bg=theme)
root.resizable(False, False)

# Input Frame
input_frame = customtkinter.CTkFrame(
    root,
    fg_color= "#427D9D",
    bg_color= "#427D9D",
    width=580,
    height=330,
    corner_radius=0
)
input_frame.place(x=10, y=10)

# Image Label img_label = tk.Label(input_frame, bg = theme)
img_label = tk.Label(
    input_frame,
    text="PREVIEW IMAGE",
    font=customtkinter.CTkFont(size=12),
    bg="#427D9D",
    fg="#31304D" 
)
img_label.place(x=350, y=-5)

# Input Box
input_box = customtkinter.CTkTextbox(
    input_frame,
    width=490,
    height=105,
    fg_color= "#164863",
    corner_radius=0,
    font=customtkinter.CTkFont(size=14),
    wrap='word',
    activate_scrollbars=True,
    text_color='#9BBEC8'
)
input_box.place(x=85, y=130)

# Encode button
encode_button = customtkinter.CTkButton(
    input_frame,
    text="Encode Text",
    fg_color= '#9BBEC8',
    corner_radius=5,
    command=encode_text,
    text_color="#31304D"
)
encode_button.place(x=85, y=244)

# Decode button
decode_button = customtkinter.CTkButton(
    input_frame,
    fg_color= '#9BBEC8',
    text="Decode Text",
    corner_radius=5,
    command=decode_text,
    text_color="#31304D"
)
decode_button.place(x=435, y=244)

# Result label
result_label = customtkinter.CTkLabel(
    input_frame,
    text="(Decode Message Location)",
    font=customtkinter.CTkFont(size=12),
    wraplength=400,
    text_color='#31304D'
)
result_label.place(x=90, y=100)

label_inputTxt = customtkinter.CTkLabel(
    input_frame,
    text="Input text here :",
    font=customtkinter.CTkFont(size=12),
    wraplength=400,
    text_color='#31304D'
)
label_inputTxt.place(x=1, y=130)
# Application
root.mainloop()
