import glob

dir = "output/"
for file in glob.glob(dir + "*"):
        print file[len(dir):]
        with open(file) as f:
                lines = f.readlines()
        le = len(lines)
        if le % 2 != 0:
                raise Exception("Not Even")
        for i in range(le / 2):
                s1 = lines[i].split(' ')
                s2 = lines[i + le / 2].split(' ')
                if s1[0] != s2[0] or len(s1) != len(s2):
                        raise Exception("Not matched")
                for j in range(2, len(s1)):
                        if s1[j] != s2[j]:
                                raise Exception("Not matched")
                pr1 = float(s1[1])
                pr2 = float(s2[1])
                print abs(pr1 - pr2), s1[0], pr1, pr2
        print ""
                                
                        
