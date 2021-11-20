import os

if __name__ == '__main__':
    files = []
    for file in os.listdir("E:/Documents/Courses/DOSP/Projs/Proj04/PartOne/demo01"):
        if file.endswith(".fs"):
            files.append(file)
    pass
