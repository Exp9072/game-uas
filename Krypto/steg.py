import tkinter as tk
from tkinter import filedialog
from stegano import lsb
from PIL import Image

def encode_text():
    original_image_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif")])
    if original_image_path:
        secret_text = entry_text.get()
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

# GUI setup
root = tk.Tk()
root.title("Text Steganography")

entry_label = tk.Label(root, text="Enter Text:")
entry_text = tk.Entry(root, width=40)
encode_button = tk.Button(root, text="Encode Text", command=encode_text)
decode_button = tk.Button(root, text="Decode Text", command=decode_text)
result_label = tk.Label(root, text="", font=("Helvetica", 10), wraplength=300)

entry_label.grid(row=0, column=0, pady=5)
entry_text.grid(row=0, column=1, pady=5)
encode_button.grid(row=1, column=0, pady=10)
decode_button.grid(row=1, column=1, pady=10)
result_label.grid(row=2, column=0, columnspan=2, pady=10)

root.mainloop()