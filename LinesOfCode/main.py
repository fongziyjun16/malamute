import os


class FileLineCounter:
    __totalLine = 0

    @classmethod
    def __count_file(cls, file_path):
        count = 0
        file = open(file_path)
        for line in file:
            if line != "\n":
                count += 1
                cls.__totalLine += 1
        file.close()
        print(file_path + ": " + str(count))

    @classmethod
    def __count_dir(cls, dir_path):
        items = os.scandir(dir_path)
        for item in items:
            item_path = dir_path + "/" + item.name
            if os.path.isfile(item_path) and item_path[-3:] == ".fs":
                cls.__count_file(item_path)
            elif os.path.isdir(item_path):
                cls.__count_dir(item_path)

    @classmethod
    def count(cls, dir_path):
        cls.__count_dir(dir_path)
        return cls.__totalLine


if __name__ == '__main__':
    dirPath = "E:/Documents/Courses/DOSP/Projs/Proj03/ChordP2PSystemSimulator"
    print("total: " + str(FileLineCounter.count(dirPath)))
