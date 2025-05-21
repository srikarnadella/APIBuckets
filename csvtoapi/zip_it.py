import zipfile
import os

with zipfile.ZipFile("deploy.zip", "w", zipfile.ZIP_DEFLATED) as zipf:
    for folder in ["build/libs", ".ebextensions"]:
        for root, _, files in os.walk(folder):
            for file in files:
                path = os.path.join(root, file)
                zipf.write(path, arcname=path.replace("\\", "/"))
    zipf.write("Procfile", arcname="Procfile")

with zipfile.ZipFile('deploy.zip', 'r') as zipf:
    print("Files in deploy.zip:")
    for name in zipf.namelist():
        print(name)