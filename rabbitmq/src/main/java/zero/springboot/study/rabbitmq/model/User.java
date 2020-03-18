package zero.springboot.study.rabbitmq.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String name;

    private String pass;

}