ODIR = src/obj
IDIR = include
SDIR = src

CC = gcc
CFLAGS = -ansi -g -Og -std=c11 -I$(IDIR)
EXE = program

OPTIMUS = -O3 -D_GNU_SOURCE $(CFLAGS)




DEPS = $(IDIR)/$(wildcard*.h)
SOURCES = $(wildcard $(SDIR)/*.c)

OBJECTS = $(foreach o, $(patsubst %.c,%.o,$(SOURCES)), $(ODIR)/$o)
LIBS = `pkg-config --cflags --libs glib-2.0`

$(ODIR)/%.o : %.c $(DEPS)
	$(CC) $(OPTIMUS) $(LIBS) -c -o $@ $< 

$(EXE): $(OBJECTS)
	$(CC) $(OPTIMUS) $(OBJECTS) -o $(EXE) $(LIBS)

clean:
	rm $(ODIR)/$(SDIR)/*
	rm $(EXE)


