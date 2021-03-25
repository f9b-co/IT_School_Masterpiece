package fr.formation.masterpieceApi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    @Autowired
    private ModelMapper mapper;

    public AbstractService() {
        //
    }

    protected final ModelMapper getMapper() {
	return mapper;
    }
}
