package bafiak.adapter.inbound;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bafiak.application.DivisorsMappingService;
import bafiak.domain.Input;
import bafiak.domain.MappingNumber;
import bafiak.domain.MappingsName;
import bafiak.domain.Output;
import lombok.RequiredArgsConstructor;

// TODO this adpater test is needed.
@RequiredArgsConstructor
public class HttpController  extends HttpServlet {
    private final DivisorsMappingService divisorsMappingService;

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException, IOException {
        final String mappingsNameRaw = request.getParameter("mappings_name");
        final MappingsName mappingsName = MappingsName.of(mappingsNameRaw);
        final String[] mappingNumbersRaw = request.getParameterValues("mapping_numbers");
        final Set<MappingNumber> mappingNumbers
                = Arrays.stream(mappingNumbersRaw).map(Integer::parseInt).map(MappingNumber::of).collect(toSet());
        final Input input = Input.of(mappingsName, mappingNumbers);

        final List<Output> divisorsWithMappings = divisorsMappingService.findDivisorsWithMappings(input);
        response.getWriter().print(covertToHtml(divisorsWithMappings));
    }

    private String covertToHtml(final List<Output> divisorsWithMappings) {
        return "<HTML>"+ divisorsWithMappings + "<HTML/>"; // TODO nice HTML convertion
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse resp) throws ServletException, IOException {
//        divisorsMappingService.addMapping(); TODO similarly as in doGet(): read HTTP request's params and covnert to domain objects
    }
}
