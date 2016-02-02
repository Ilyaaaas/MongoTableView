package mongotableview;

import org.bson.types.ObjectId;

class Student {
    ObjectId _id;
    String name;
    String gruppa;

    
        public Student() {
    }

    public Student(ObjectId _id, String name, String gruppa) {
        this._id = _id;
        this.name = name;
        this.gruppa = gruppa;
    }
    
    public ObjectId getId() {
            return _id;
    }

    public void setId(ObjectId id) {
            this._id = id;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getGruppa() {
            return gruppa;
    }

    public void setGruppa(String gruppa) {
            this.gruppa = gruppa;
    }
    
}
