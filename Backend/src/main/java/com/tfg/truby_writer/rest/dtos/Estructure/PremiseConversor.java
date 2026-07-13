package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import com.tfg.truby_writer.model.entities.Premise;
import com.tfg.truby_writer.model.services.Block;

import java.util.List;
import java.util.stream.Collectors;

public final class PremiseConversor {

    private PremiseConversor() {}

    public static PremiseDto toPremiseDto(Premise premise) {
        if (premise == null) return null;
        return PremiseDto.builder()
                .id(premise.getId())
                .premise(premise.getPremise())
                .narrativePossibilities(premise.getNarrativePossibilities())
                .narrativeChallenges(premise.getNarrativeChallenges())
                .problems(premise.getProblems())
                .foundingPrinciple(premise.getFoundingPrinciple())
                .conflict(premise.getConflict())
                .moralDecision(premise.getMoralDecision())
                .plotId(premise.getPlot() != null ? premise.getPlot().getId() : null)
                .build();
    }

    public static List<PremiseDto> toPremiseDtos(List<Premise> premises) {
        return premises.stream().map(PremiseConversor::toPremiseDto).collect(Collectors.toList());
    }

    public static Block<PremiseDto> toPremiseBlockDto(Block<Premise> premiseBlock) {
        List<PremiseDto> items = toPremiseDtos(premiseBlock.getItems());
        return new Block<>(items, premiseBlock.getExistMoreItems());
    }
}